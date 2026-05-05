package com.roomiesguard.backend.service;

import com.roomiesguard.backend.dto.OccupancyResponse;
import com.roomiesguard.backend.entity.Bed;
import com.roomiesguard.backend.entity.Room;
import com.roomiesguard.backend.repository.BedRepository;
import com.roomiesguard.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

    public PropertyService(RoomRepository roomRepository, BedRepository bedRepository) {
        this.roomRepository = roomRepository;
        this.bedRepository = bedRepository;
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Bed createBed(Bed bed) {
        return bedRepository.save(bed);
    }

    public List<Room> allRooms() {
        return roomRepository.findAllByOrderByFloorAscNumberAsc();
    }

    public List<Bed> allBeds() {
        return bedRepository.findAll();
    }

    public OccupancyResponse occupancy() {
        long total = bedRepository.count();
        long occupied = bedRepository.findAll().stream().filter(b -> b.getTenantId() != null && !b.getTenantId().isBlank()).count();
        return new OccupancyResponse(total, occupied, total - occupied);
    }
}
