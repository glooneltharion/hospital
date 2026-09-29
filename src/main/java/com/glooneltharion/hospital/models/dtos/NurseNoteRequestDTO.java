package com.glooneltharion.hospital.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NurseNoteRequestDTO {
    private Long patientId;
    private String note;
}
