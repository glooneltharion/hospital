package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.NurseNote;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.repositories.NurseNoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseNoteServiceImpl implements NurseNoteService {

    private final NurseNoteRepository repository;

    public NurseNoteServiceImpl(NurseNoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public NurseNote create(NurseNote note) {
        return repository.save(note);
    }

    @Override
    public List<NurseNote> getByPatient(Patient patient) {
        return repository.findByPatient(patient);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
