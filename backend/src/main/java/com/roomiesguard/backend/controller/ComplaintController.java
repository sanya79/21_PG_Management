package com.roomiesguard.backend.controller;

import com.roomiesguard.backend.dto.UpdateStatusRequest;
import com.roomiesguard.backend.entity.Complaint;
import com.roomiesguard.backend.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public Complaint create(@RequestBody Complaint complaint) {
        return complaintService.create(complaint);
    }

    @GetMapping
    public List<Complaint> all() {
        return complaintService.all();
    }

    @PatchMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable String id, @RequestBody UpdateStatusRequest request) {
        return complaintService.updateStatus(id, request);
    }
}
