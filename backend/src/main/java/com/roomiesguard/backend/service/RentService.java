package com.roomiesguard.backend.service;

import com.roomiesguard.backend.dto.ProofRequest;
import com.roomiesguard.backend.entity.RentRecord;
import com.roomiesguard.backend.exception.NotFoundException;
import com.roomiesguard.backend.repository.RentRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentService {

    private final RentRecordRepository rentRecordRepository;

    public RentService(RentRecordRepository rentRecordRepository) {
        this.rentRecordRepository = rentRecordRepository;
    }

    public RentRecord create(RentRecord rentRecord) {
        rentRecord.setId(rentRecord.getId() == null ? "rent-" + UUID.randomUUID().toString().substring(0, 8) : rentRecord.getId());
        rentRecord.setStatus(rentRecord.getStatus() == null ? "pending" : rentRecord.getStatus());
        return rentRecordRepository.save(rentRecord);
    }

    public List<RentRecord> all() {
        return rentRecordRepository.findAll();
    }

    public List<RentRecord> byTenant(String tenantId) {
        return rentRecordRepository.findByTenantIdOrderByMonthDesc(tenantId);
    }

    public RentRecord markPaid(String id) {
        RentRecord rentRecord = rentRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rent record not found"));
        rentRecord.setStatus("paid");
        return rentRecordRepository.save(rentRecord);
    }

    public RentRecord submitProof(String id, ProofRequest request) {
        RentRecord rentRecord = rentRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rent record not found"));
        rentRecord.setProof(request.getProof());
        rentRecord.setStatus("submitted");
        return rentRecordRepository.save(rentRecord);
    }
}
