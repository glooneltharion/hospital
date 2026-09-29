package com.glooneltharion.hospital.mapper;

import com.glooneltharion.hospital.models.Prescription;
import com.glooneltharion.hospital.models.dtos.PrescriptionRequestDTO;
import com.glooneltharion.hospital.models.dtos.PrescriptionResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(source = "patient.id", target = "patientId")
    PrescriptionResponseDTO toDTO(Prescription prescription);

    Prescription toEntity(PrescriptionRequestDTO dto);
}
