package com.glooneltharion.hospital.controllers;

import com.glooneltharion.hospital.models.dtos.PatientDTO;
import com.glooneltharion.hospital.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    void shouldDisplayPatientsPage() throws Exception {

        when(patientService.getAllPatients())
                .thenReturn(List.of());

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("page", "patients"));

        verify(patientService).getAllPatients();
    }

    @Test
    void shouldCreateNewPatientAndRedirect() throws Exception {

        PatientDTO dto = new PatientDTO();

        when(patientService.createPatient(any(PatientDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(
                        post("/patients/save")
                                .param("firstName", "John")
                                .param("lastName", "Smith")
                                .param("email", "john@test.com")
                                .param("phoneNumber", "+421900123456")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));

        verify(patientService)
                .createPatient(any(PatientDTO.class));
    }

    @Test
    void shouldReturnPatientsPageWhenValidationFails() throws Exception {

        when(patientService.getAllPatients())
                .thenReturn(List.of());

        mockMvc.perform(
                        post("/patients/save")
                                .param("firstName", "John")
                                .param("lastName", "Smith")
                                .param("email", "john@test.com")
                                .param("phoneNumber", "123456")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("patient"));

        verify(patientService)
                .getAllPatients();

        verify(patientService, never())
                .createPatient(any(PatientDTO.class));
    }
}

