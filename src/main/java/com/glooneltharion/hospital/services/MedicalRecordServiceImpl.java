package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.MedicalRecord;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.repositories.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordServiceImpl(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicalRecord createRecord(MedicalRecord record) {
        return repository.save(record);
    }

    @Override
    public List<MedicalRecord> getPatientRecords(Patient patient) {
        return repository.findByPatient(patient);
    }

    @Override
    public MedicalRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }
}
