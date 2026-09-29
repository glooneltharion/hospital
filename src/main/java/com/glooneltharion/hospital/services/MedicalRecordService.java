package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.MedicalRecord;
import com.glooneltharion.hospital.models.Patient;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecord createRecord(MedicalRecord record);

    List<MedicalRecord> getPatientRecords(Patient patient);

    MedicalRecord getById(Long id);
}
