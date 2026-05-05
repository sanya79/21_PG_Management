package com.roomiesguard.backend.dto;

public class OccupancyResponse {
    private long totalBeds;
    private long occupiedBeds;
    private long vacantBeds;

    public OccupancyResponse(long totalBeds, long occupiedBeds, long vacantBeds) {
        this.totalBeds = totalBeds;
        this.occupiedBeds = occupiedBeds;
        this.vacantBeds = vacantBeds;
    }

    public long getTotalBeds() { return totalBeds; }
    public long getOccupiedBeds() { return occupiedBeds; }
    public long getVacantBeds() { return vacantBeds; }
}
