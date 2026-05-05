package com.roomiesguard.backend.controller;

import com.roomiesguard.backend.dto.OccupancyResponse;
import com.roomiesguard.backend.entity.Bed;
import com.roomiesguard.backend.entity.Room;
import com.roomiesguard.backend.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody Room room) {
        return propertyService.createRoom(room);
    }

    @GetMapping("/rooms")
    public List<Room> rooms() {
        return propertyService.allRooms();
    }

    @PostMapping("/beds")
    public Bed createBed(@RequestBody Bed bed) {
        return propertyService.createBed(bed);
    }

    @GetMapping("/beds")
    public List<Bed> beds() {
        return propertyService.allBeds();
    }

    @GetMapping("/occupancy")
    public OccupancyResponse occupancy() {
        return propertyService.occupancy();
    }
}
