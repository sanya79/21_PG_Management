package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.RentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRecordRepository extends JpaRepository<RentRecord, String> {
    List<RentRecord> findByTenantIdOrderByMonthDesc(String tenantId);
}
