package com.roomiesguard.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "visitor_logs")
public class VisitorLog {

    @Id
    private String id;
    private String visitorName;
    private String phone;
    private String purpose;
    private String tenantId;
    private String photo;
    private String status;
    private OffsetDateTime timeIn;
    private OffsetDateTime timeOut;
    private OffsetDateTime createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public OffsetDateTime getTimeIn() { return timeIn; }
    public void setTimeIn(OffsetDateTime timeIn) { this.timeIn = timeIn; }
    public OffsetDateTime getTimeOut() { return timeOut; }
    public void setTimeOut(OffsetDateTime timeOut) { this.timeOut = timeOut; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
