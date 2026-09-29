package com.glooneltharion.hospital.mapper;

import com.glooneltharion.hospital.models.NurseNote;
import com.glooneltharion.hospital.models.dtos.NurseNoteRequestDTO;
import com.glooneltharion.hospital.models.dtos.NurseNoteResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NurseNoteMapper {

    @Mapping(source = "patient.id", target = "patientId")
    NurseNoteResponseDTO toDTO(NurseNote note);

    NurseNote toEntity(NurseNoteRequestDTO dto);
}
