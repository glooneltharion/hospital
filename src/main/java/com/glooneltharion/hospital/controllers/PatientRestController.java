package com.glooneltharion.hospital.controllers;

import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.dtos.PatientRestDTO;
import com.glooneltharion.hospital.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/patients")
public class PatientRestController {
    private final PatientService patientService;

    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientRestDTO> getAll() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public PatientRestDTO getById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @PostMapping
    public Patient create(@Valid @RequestBody PatientRestDTO dto) {
        return patientService.save(dto);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id,
                          @Valid @RequestBody PatientRestDTO dto) {

        return patientService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        patientService.delete(id);
    }
}
