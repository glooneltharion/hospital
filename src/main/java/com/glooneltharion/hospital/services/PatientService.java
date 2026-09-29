package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.dtos.PatientDTO;
import com.glooneltharion.hospital.models.dtos.PatientRestDTO;

import java.util.List;

public interface PatientService {

    PatientDTO createPatient(PatientDTO dto);

    List<PatientDTO> getAllPatients();
    List<PatientDTO> getAllPatientsSortedByParameter(String parameter);

    PatientDTO getPatientById(Long id);

    List<PatientDTO> searchByLastName(String lastName);

    void updatePatient(Long id, PatientDTO dto);

    void deletePatient(Long id);

    List<PatientRestDTO> findAll();

    PatientRestDTO findById(Long id);

    Patient save(PatientRestDTO dto);

    Patient update(Long id, PatientRestDTO dto);

    void delete(Long id);
}