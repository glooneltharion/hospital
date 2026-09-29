package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.Prescription;
import com.glooneltharion.hospital.repositories.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;

    public PrescriptionServiceImpl(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Prescription create(Prescription prescription) {
        return repository.save(prescription);
    }

    @Override
    public List<Prescription> getByPatient(Patient patient) {
        return repository.findByPatient(patient);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
