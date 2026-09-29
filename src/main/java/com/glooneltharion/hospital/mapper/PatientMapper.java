package com.glooneltharion.hospital.mapper;

import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.dtos.PatientDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientDTO dto);

    PatientDTO toDTO(Patient patient);



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(PatientDTO dto,
                       @MappingTarget Patient patient);
}
