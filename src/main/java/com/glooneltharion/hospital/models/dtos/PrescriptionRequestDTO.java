package com.glooneltharion.hospital.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequestDTO {
    private Long patientId;
    private String medication;
    private String dosage;
    private String instructions;
}
