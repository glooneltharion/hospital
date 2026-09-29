package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.AuditLog;
import com.glooneltharion.hospital.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogServiceImpl(AuditLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuditLog log(AuditLog log) {
        return repository.save(log);
    }
}
