package com.glooneltharion.hospital.controllers;

import com.glooneltharion.hospital.models.dtos.AppointmentRequestDTO;
import com.glooneltharion.hospital.services.AppointmentService;
import com.glooneltharion.hospital.services.PatientService;
import com.glooneltharion.hospital.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService, UserService userService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.userService = userService;

        this.patientService = patientService;
    }

    @GetMapping
    public String page(Model model) {
        model.addAttribute("appointments", appointmentService.getAll());
        model.addAttribute("appointment", new AppointmentRequestDTO());
        model.addAttribute("doctors", userService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("page", "appointments");

        return "appointments";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AppointmentRequestDTO dto, BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("appointments", appointmentService.getAll());
            return "appointments";
        }
        if (dto.getId() != null) {
            appointmentService.update(dto.getId(), dto);
        } else {
            appointmentService.createAppointment(dto);
        }

        return "redirect:/appointments";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute(
                "appointment",
                appointmentService.getById(id)
        );

        model.addAttribute("appointments", appointmentService.getAll());

        model.addAttribute("doctors", userService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());

        return "appointments";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {

        appointmentService.cancel(id);

        return "redirect:/appointments";
    }
}
