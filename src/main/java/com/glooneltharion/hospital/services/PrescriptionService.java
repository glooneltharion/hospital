package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.Prescription;

import java.util.List;

public interface PrescriptionService {

    Prescription create(Prescription prescription);

    List<Prescription> getByPatient(Patient patient);

    void delete(Long id);
}