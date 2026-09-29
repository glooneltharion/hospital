package com.glooneltharion.hospital.controllers;

import com.glooneltharion.hospital.models.dtos.PatientDTO;
import com.glooneltharion.hospital.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String page(Model model, @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (Objects.nonNull(sortBy)) {
            model.addAttribute("patients", patientService.getAllPatientsSortedByParameter(sortBy));
        } else {
            model.addAttribute("patients", patientService.getAllPatients());
        }

        model.addAttribute("patient", new PatientDTO());
        model.addAttribute("page", "patients");
        return "patients";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("patient") PatientDTO dto,
                       BindingResult result,
                       Model model) {

        if (result.hasErrors()) {

            model.addAttribute("patient", dto);
            model.addAttribute("patients", patientService.getAllPatients());

            return "patients";
        }

        if (dto.getId() != null) {
            patientService.updatePatient(dto.getId(), dto);
        } else {
            patientService.createPatient(dto);
        }

        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        PatientDTO patient = patientService.getPatientById(id);

        model.addAttribute("patient", patient);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("page", "patients");

        return "patients";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
