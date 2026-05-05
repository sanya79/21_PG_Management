package com.roomiesguard.backend.repository;

import com.roomiesguard.backend.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    List<Notice> findAllByOrderByCreatedAtDesc();
}
