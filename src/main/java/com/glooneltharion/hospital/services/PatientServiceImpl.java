package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.mapper.PatientMapper;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.dtos.PatientDTO;
import com.glooneltharion.hospital.models.dtos.PatientRestDTO;
import com.glooneltharion.hospital.repositories.PatientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    public PatientServiceImpl(PatientRepository repository,
                              PatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PatientDTO createPatient(PatientDTO dto) {
        Patient patient = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(patient));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<PatientDTO> getAllPatientsSortedByParameter(String parameter) {
        return repository.findAll(Sort.by(parameter))
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        return mapper.toDTO(
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Patient not found"))
        );
    }

    @Override
    public List<PatientDTO> searchByLastName(String lastName) {
        return repository.findByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void updatePatient(Long id, PatientDTO dto) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        mapper.updateFromDTO(dto, patient);

        mapper.toDTO(repository.save(patient));
    }

    @Override
    public void deletePatient(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<PatientRestDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToRestDTO)
                .toList();

    }

    @Override
    public PatientRestDTO findById(Long id) {
        return mapToRestDTO( repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found")));

    }
    @Override
    public Patient save(PatientRestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Patient patient = new Patient();

        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());

        return repository.save(patient);
    }

    @Override
    public Patient update(Long id, PatientRestDTO dto) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());

        return repository.save(patient);
    }

    @Override
    public void delete(Long id) {

        Patient patient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        try {
            repository.delete(patient);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "Cannot delete patient because related records exist"
            );
        }
    }


    private PatientRestDTO mapToRestDTO(Patient patient) {
        PatientRestDTO dto = new PatientRestDTO();
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setEmail(patient.getEmail());
        return dto;
    }
}
