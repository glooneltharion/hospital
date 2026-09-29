package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldFindPatientsByLastNameIgnoringCase() {

        Patient patient = new Patient();

        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setEmail("john.smith@test.com");

        patientRepository.save(patient);

        List<Patient> result =
                patientRepository.findByLastNameContainingIgnoreCase("sMiTh");

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {

        Patient patient = new Patient();

        patient.setFirstName("Anna");
        patient.setLastName("Brown");
        patient.setEmail("anna.brown@test.com");

        patientRepository.save(patient);

        assertTrue(
                patientRepository.existsByEmail("anna.brown@test.com")
        );
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {

        assertFalse(
                patientRepository.existsByEmail("unknown@test.com")
        );
    }
}