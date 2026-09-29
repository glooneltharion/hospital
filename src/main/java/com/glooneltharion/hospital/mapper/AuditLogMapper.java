package com.glooneltharion.hospital.mapper;

import com.glooneltharion.hospital.models.AuditLog;
import com.glooneltharion.hospital.models.dtos.AuditLogDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    AuditLogDTO toDTO(AuditLog log);

    AuditLog toEntity(AuditLogDTO dto);
}