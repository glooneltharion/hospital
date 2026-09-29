package com.glooneltharion.hospital.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class MedicalRecordResponseDTO {
    private Long id;
    private Long patientId;
    private String diagnosis;
    private String treatment;
    private String notes;
    private LocalDateTime createdAt;
}
