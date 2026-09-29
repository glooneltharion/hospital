package com.glooneltharion.hospital.services;


import com.glooneltharion.hospital.models.dtos.AppointmentRequestDTO;
import com.glooneltharion.hospital.models.dtos.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {

    void createAppointment(AppointmentRequestDTO dto);

    List<AppointmentResponseDTO> getAll();
    AppointmentRequestDTO getById(Long id);

    void update(Long id, AppointmentRequestDTO dto);

    void cancel(Long id);
}
