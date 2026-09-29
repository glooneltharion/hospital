package com.glooneltharion.hospital.controllers;

import tools.jackson.databind.ObjectMapper;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.dtos.PatientRestDTO;
import com.glooneltharion.hospital.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientRestController.class)
class PatientRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientService patientService;

    @Test
    void shouldReturnAllPatients() throws Exception {

        PatientRestDTO patient = new PatientRestDTO();

        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setEmail("john@test.com");

        when(patientService.findAll())
                .thenReturn(List.of(patient));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[0].email").value("john@test.com"));

        verify(patientService).findAll();
    }

    @Test
    void shouldCreatePatient() throws Exception {

        PatientRestDTO dto = new PatientRestDTO();

        dto.setFirstName("Anna");
        dto.setLastName("Brown");
        dto.setEmail("anna@test.com");

        Patient savedPatient = new Patient();

        savedPatient.setFirstName("Anna");
        savedPatient.setLastName("Brown");
        savedPatient.setEmail("anna@test.com");

        when(patientService.save(any(PatientRestDTO.class)))
                .thenReturn(savedPatient);

        mockMvc.perform(
                        post("/api/patients")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Brown"))
                .andExpect(jsonPath("$.email").value("anna@test.com"));

        verify(patientService)
                .save(any(PatientRestDTO.class));
    }

    @Test
    void shouldDeletePatient() throws Exception {

        doNothing()
                .when(patientService)
                .delete(1L);

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk());

        verify(patientService)
                .delete(1L);
    }
}
