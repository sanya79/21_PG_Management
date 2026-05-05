package com.roomiesguard.backend.service;

import com.roomiesguard.backend.dto.AssignBedRequest;
import com.roomiesguard.backend.dto.UnassignBedRequest;
import com.roomiesguard.backend.entity.Bed;
import com.roomiesguard.backend.entity.Tenant;
import com.roomiesguard.backend.exception.BadRequestException;
import com.roomiesguard.backend.exception.NotFoundException;
import com.roomiesguard.backend.repository.BedRepository;
import com.roomiesguard.backend.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final BedRepository bedRepository;

    public TenantService(TenantRepository tenantRepository, BedRepository bedRepository) {
        this.tenantRepository = tenantRepository;
        this.bedRepository = bedRepository;
    }

    public List<Tenant> getAll() {
        return tenantRepository.findAll();
    }

    public Tenant create(Tenant tenant) {
        tenant.setId(tenant.getId() == null ? "t-" + UUID.randomUUID().toString().substring(0, 8) : tenant.getId());
        tenant.setJoinedAt(tenant.getJoinedAt() == null ? LocalDate.now().toString() : tenant.getJoinedAt());
        return tenantRepository.save(tenant);
    }

    public Tenant assignBed(AssignBedRequest request) {
        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        Bed bed = bedRepository.findById(request.getBedId())
                .orElseThrow(() -> new NotFoundException("Bed not found"));

        if (bed.getTenantId() != null && !bed.getTenantId().isBlank()) {
            throw new BadRequestException("Bed is already occupied");
        }

        if (tenant.getBedId() != null && !tenant.getBedId().isBlank()) {
            Bed existingBed = bedRepository.findById(tenant.getBedId()).orElse(null);
            if (existingBed != null) {
                existingBed.setTenantId(null);
                bedRepository.save(existingBed);
            }
        }

        bed.setTenantId(tenant.getId());
        tenant.setBedId(bed.getId());
        tenant.setRoomId(bed.getRoomId());

        bedRepository.save(bed);
        return tenantRepository.save(tenant);
    }

    public void unassignBed(UnassignBedRequest request) {
        Bed bed = bedRepository.findById(request.getBedId())
                .orElseThrow(() -> new NotFoundException("Bed not found"));

        if (bed.getTenantId() != null && !bed.getTenantId().isBlank()) {
            Tenant tenant = tenantRepository.findById(bed.getTenantId()).orElse(null);
            if (tenant != null) {
                tenant.setBedId(null);
                tenant.setRoomId(null);
                tenantRepository.save(tenant);
            }
        }

        bed.setTenantId(null);
        bedRepository.save(bed);
    }

    public void removeTenant(String id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));

        if (tenant.getBedId() != null && !tenant.getBedId().isBlank()) {
            Bed bed = bedRepository.findById(tenant.getBedId()).orElse(null);
            if (bed != null) {
                bed.setTenantId(null);
                bedRepository.save(bed);
            }
        }

        tenantRepository.deleteById(id);
    }
}
