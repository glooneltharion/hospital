package com.glooneltharion.hospital.mapper;

import com.glooneltharion.hospital.models.Appointment;
import com.glooneltharion.hospital.models.dtos.AppointmentResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor.firstName", target = "doctorFirstName")
    @Mapping(source = "doctor.lastName", target = "doctorLastName")

    @Mapping(source = "patient.firstName", target = "patientFirstName")
    @Mapping(source = "patient.lastName", target = "patientLastName")

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")

    @Mapping(source = "status", target = "status")
    AppointmentResponseDTO toDTO(Appointment appointment);
}
