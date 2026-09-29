package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByLastNameContainingIgnoreCase(String lastName);

    List<Patient> findByInsuranceCompany(String insuranceCompany);
    boolean existsByEmail(String email);
}
