package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, String> {
    List<Complaint> findAllByOrderByCreatedAtDesc();
}
