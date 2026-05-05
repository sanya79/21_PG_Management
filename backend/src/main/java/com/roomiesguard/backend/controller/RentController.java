package com.roomiesguard.backend.controller;

import com.roomiesguard.backend.dto.ProofRequest;
import com.roomiesguard.backend.entity.RentRecord;
import com.roomiesguard.backend.service.RentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping
    public RentRecord create(@RequestBody RentRecord rentRecord) {
        return rentService.create(rentRecord);
    }

    @GetMapping
    public List<RentRecord> all() {
        return rentService.all();
    }

    @GetMapping("/tenant/{tenantId}")
    public List<RentRecord> byTenant(@PathVariable String tenantId) {
        return rentService.byTenant(tenantId);
    }

    @PatchMapping("/{id}/paid")
    public RentRecord markPaid(@PathVariable String id) {
        return rentService.markPaid(id);
    }

    @PatchMapping("/{id}/proof")
    public RentRecord submitProof(@PathVariable String id, @RequestBody ProofRequest request) {
        return rentService.submitProof(id, request);
    }
}
