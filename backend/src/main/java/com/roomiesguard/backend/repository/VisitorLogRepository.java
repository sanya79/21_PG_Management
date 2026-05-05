package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.VisitorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorLogRepository extends JpaRepository<VisitorLog, String> {
    List<VisitorLog> findByTenantIdOrderByCreatedAtDesc(String tenantId);
    List<VisitorLog> findAllByOrderByCreatedAtDesc();
}
