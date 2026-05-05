package com.roomiesguard.backend.service;

import com.roomiesguard.backend.dto.UpdateStatusRequest;
import com.roomiesguard.backend.entity.VisitorLog;
import com.roomiesguard.backend.exception.BadRequestException;
import com.roomiesguard.backend.exception.NotFoundException;
import com.roomiesguard.backend.repository.VisitorLogRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class VisitorService {

    private static final Set<String> ALLOWED_STATUS = Set.of("pending", "approved", "denied");

    private final VisitorLogRepository visitorLogRepository;

    public VisitorService(VisitorLogRepository visitorLogRepository) {
        this.visitorLogRepository = visitorLogRepository;
    }

    public VisitorLog create(VisitorLog visitorLog) {
        visitorLog.setId(visitorLog.getId() == null ? "v-" + UUID.randomUUID().toString().substring(0, 8) : visitorLog.getId());
        visitorLog.setStatus("pending");
        OffsetDateTime now = OffsetDateTime.now();
        visitorLog.setTimeIn(now);
        visitorLog.setCreatedAt(now);
        return visitorLogRepository.save(visitorLog);
    }

    public List<VisitorLog> all() {
        return visitorLogRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<VisitorLog> byTenant(String tenantId) {
        return visitorLogRepository.findByTenantIdOrderByCreatedAtDesc(tenantId);
    }

    public VisitorLog updateStatus(String id, UpdateStatusRequest request) {
        VisitorLog visitorLog = visitorLogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visitor not found"));
        String status = request.getStatus() == null ? "" : request.getStatus().trim().toLowerCase();
        if (!ALLOWED_STATUS.contains(status)) {
            throw new BadRequestException("Status must be pending, approved, or denied");
        }
        visitorLog.setStatus(status);
        return visitorLogRepository.save(visitorLog);
    }

    public VisitorLog approve(String id) {
        return updateStatus(id, statusRequest("approved"));
    }

    public VisitorLog deny(String id) {
        return updateStatus(id, statusRequest("denied"));
    }

    public VisitorLog checkOut(String id) {
        VisitorLog visitorLog = visitorLogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visitor not found"));
        if (!"approved".equals(visitorLog.getStatus())) {
            throw new BadRequestException("Only approved visitors can be checked out");
        }
        visitorLog.setTimeOut(OffsetDateTime.now());
        return visitorLogRepository.save(visitorLog);
    }

    private UpdateStatusRequest statusRequest(String status) {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setStatus(status);
        return request;
    }
}
