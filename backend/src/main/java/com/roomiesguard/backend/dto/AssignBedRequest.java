package com.roomiesguard.backend.dto;

public class AssignBedRequest {
    private String tenantId;
    private String bedId;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getBedId() { return bedId; }
    public void setBedId(String bedId) { this.bedId = bedId; }
}
