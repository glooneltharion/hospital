package com.glooneltharion.hospital.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AppointmentResponseDTO {
    private Long id;

    private String doctorFirstName;
    private String doctorLastName;

    private String patientFirstName;
    private String patientLastName;

    private String status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
