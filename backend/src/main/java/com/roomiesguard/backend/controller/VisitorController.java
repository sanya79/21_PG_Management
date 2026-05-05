package com.roomiesguard.backend.controller;

import com.roomiesguard.backend.dto.UpdateStatusRequest;
import com.roomiesguard.backend.entity.VisitorLog;
import com.roomiesguard.backend.service.VisitorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    public VisitorLog create(@RequestBody VisitorLog visitorLog) {
        return visitorService.create(visitorLog);
    }

    @GetMapping
    public List<VisitorLog> all() {
        return visitorService.all();
    }

    @GetMapping("/tenant/{tenantId}")
    public List<VisitorLog> byTenant(@PathVariable String tenantId) {
        return visitorService.byTenant(tenantId);
    }

    @PatchMapping("/{id}/status")
    public VisitorLog updateStatus(@PathVariable String id, @RequestBody UpdateStatusRequest request) {
        return visitorService.updateStatus(id, request);
    }

    @PatchMapping("/{id}/approve")
    public VisitorLog approve(@PathVariable String id) {
        return visitorService.approve(id);
    }

    @PatchMapping("/{id}/deny")
    public VisitorLog deny(@PathVariable String id) {
        return visitorService.deny(id);
    }

    @PatchMapping("/{id}/checkout")
    public VisitorLog checkout(@PathVariable String id) {
        return visitorService.checkOut(id);
    }
}
