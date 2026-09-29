package com.glooneltharion.hospital.mapper;

import com.glooneltharion.hospital.models.MedicalRecord;
import com.glooneltharion.hospital.models.dtos.MedicalRecordRequestDTO;
import com.glooneltharion.hospital.models.dtos.MedicalRecordResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "patient.id", target = "patientId")
    MedicalRecordResponseDTO toDTO(MedicalRecord record);

    MedicalRecord toEntity(MedicalRecordRequestDTO dto);
}
