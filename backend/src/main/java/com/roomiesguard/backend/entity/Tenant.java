package com.roomiesguard.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    private String id;
    private String name;
    private String phone;
    private String kycId;
    private String roomId;
    private String bedId;
    private String joinedAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getKycId() { return kycId; }
    public void setKycId(String kycId) { this.kycId = kycId; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getBedId() { return bedId; }
    public void setBedId(String bedId) { this.bedId = bedId; }
    public String getJoinedAt() { return joinedAt; }
    public void setJoinedAt(String joinedAt) { this.joinedAt = joinedAt; }
}
