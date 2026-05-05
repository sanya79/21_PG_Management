package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findAllByOrderByFloorAscNumberAsc();
}
