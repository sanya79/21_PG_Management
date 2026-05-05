package com.roomiesguard.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rent_records")
public class RentRecord {

    @Id
    private String id;
    private String tenantId;
    private String month;
    private Integer amount;
    private String dueDate;
    private String status;
    private String proof;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getProof() { return proof; }
    public void setProof(String proof) { this.proof = proof; }
}
