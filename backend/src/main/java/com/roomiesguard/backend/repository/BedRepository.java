package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, String> {
    List<Bed> findByRoomId(String roomId);
}
