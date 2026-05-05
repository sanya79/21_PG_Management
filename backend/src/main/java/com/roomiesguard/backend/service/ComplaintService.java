package com.roomiesguard.backend.service;

import com.roomiesguard.backend.dto.UpdateStatusRequest;
import com.roomiesguard.backend.entity.Complaint;
import com.roomiesguard.backend.exception.BadRequestException;
import com.roomiesguard.backend.exception.NotFoundException;
import com.roomiesguard.backend.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ComplaintService {

    private static final Set<String> ALLOWED_STATUS = Set.of("Open", "In Progress", "Resolved");

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint create(Complaint complaint) {
        complaint.setId(complaint.getId() == null ? "c-" + UUID.randomUUID().toString().substring(0, 8) : complaint.getId());
        complaint.setStatus(complaint.getStatus() == null ? "Open" : complaint.getStatus());
        complaint.setCreatedAt(complaint.getCreatedAt() == null ? OffsetDateTime.now() : complaint.getCreatedAt());
        return complaintRepository.save(complaint);
    }

    public List<Complaint> all() {
        return complaintRepository.findAllByOrderByCreatedAtDesc();
    }

    public Complaint updateStatus(String id, UpdateStatusRequest request) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Complaint not found"));

        if (!ALLOWED_STATUS.contains(request.getStatus())) {
            throw new BadRequestException("Complaint status must be Open, In Progress, or Resolved");
        }

        complaint.setStatus(request.getStatus());
        return complaintRepository.save(complaint);
    }
}
