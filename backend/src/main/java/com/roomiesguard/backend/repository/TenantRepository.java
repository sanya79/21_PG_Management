package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {
}
