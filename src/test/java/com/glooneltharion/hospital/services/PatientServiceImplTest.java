package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.mapper.PatientMapper;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.dtos.PatientDTO;
import com.glooneltharion.hospital.models.dtos.PatientRestDTO;
import com.glooneltharion.hospital.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PatientServiceImplTest {

    @Mock
    private PatientRepository repository;

    @Mock
    private PatientMapper mapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void shouldCreatePatient() {

        PatientDTO dto = new PatientDTO();

        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setEmail("john@test.com");

        Patient savedPatient = new Patient();
        savedPatient.setFirstName("John");
        savedPatient.setLastName("Smith");
        savedPatient.setEmail("john@test.com");

        PatientDTO resultDto = new PatientDTO();

        when(mapper.toEntity(dto)).thenReturn(patient);
        when(repository.save(patient)).thenReturn(savedPatient);
        when(mapper.toDTO(savedPatient)).thenReturn(resultDto);

        PatientDTO result = patientService.createPatient(dto);

        assertSame(resultDto, result);

        verify(mapper).toEntity(dto);
        verify(repository).save(patient);
        verify(mapper).toDTO(savedPatient);
    }

    @Test
    void shouldReturnPatientById() {

        Patient patient = new Patient();
        patient.setFirstName("Anna");
        patient.setLastName("Brown");
        patient.setEmail("anna@test.com");

        PatientDTO dto = new PatientDTO();

        when(repository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(mapper.toDTO(patient))
                .thenReturn(dto);

        PatientDTO result = patientService.getPatientById(1L);

        assertSame(dto, result);

        verify(repository).findById(1L);
        verify(mapper).toDTO(patient);
    }

    @Test
    void shouldThrowExceptionWhenPatientDoesNotExist() {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> patientService.getPatientById(999L)
        );

        assertEquals("Patient not found", exception.getMessage());

        verify(repository).findById(999L);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void shouldNotSavePatientWhenEmailAlreadyExists() {

        PatientRestDTO dto = new PatientRestDTO();
        dto.setFirstName("John");
        dto.setLastName("Smith");
        dto.setEmail("john@test.com");

        when(repository.existsByEmail("john@test.com"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> patientService.save(dto)
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(repository).existsByEmail("john@test.com");
        verify(repository, never()).save(any(Patient.class));
    }
}