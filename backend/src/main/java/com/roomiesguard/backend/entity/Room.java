package com.roomiesguard.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    private String id;
    private Integer floor;
    private String number;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
}
