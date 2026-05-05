package com.roomiesguard.backend.controller;

import com.roomiesguard.backend.dto.AssignBedRequest;
import com.roomiesguard.backend.dto.UnassignBedRequest;
import com.roomiesguard.backend.entity.Tenant;
import com.roomiesguard.backend.service.TenantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public List<Tenant> all() {
        return tenantService.getAll();
    }

    @PostMapping
    public Tenant create(@RequestBody Tenant tenant) {
        return tenantService.create(tenant);
    }

    @PostMapping("/assign-bed")
    public Tenant assignBed(@RequestBody AssignBedRequest request) {
        return tenantService.assignBed(request);
    }

    @PostMapping("/unassign-bed")
    public void unassignBed(@RequestBody UnassignBedRequest request) {
        tenantService.unassignBed(request);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable String id) {
        tenantService.removeTenant(id);
    }
}
