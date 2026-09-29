package com.glooneltharion.hospital.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordRequestDTO {
    private Long patientId;
    private String diagnosis;
    private String treatment;
    private String notes;
}
