package com.glooneltharion.hospital.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NurseNoteResponseDTO {
    private Long id;
    private Long patientId;
    private String note;
}
