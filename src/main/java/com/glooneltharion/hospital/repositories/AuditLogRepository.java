package com.glooneltharion.hospital.repositories;

import com.glooneltharion.hospital.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
