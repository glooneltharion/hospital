package com.glooneltharion.hospital.kafka;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AppointmentCreatedEvent {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentDate;

    public AppointmentCreatedEvent() {
    }

    public AppointmentCreatedEvent(
            Long appointmentId,
            Long patientId,
            Long doctorId,
            LocalDateTime appointmentDate
    ) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
    }

}
