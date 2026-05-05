package com.roomiesguard.backend.service;

import com.roomiesguard.backend.entity.Notice;
import com.roomiesguard.backend.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Notice create(Notice notice) {
        notice.setId(notice.getId() == null ? "n-" + UUID.randomUUID().toString().substring(0, 8) : notice.getId());
        notice.setCreatedAt(notice.getCreatedAt() == null ? OffsetDateTime.now() : notice.getCreatedAt());
        return noticeRepository.save(notice);
    }

    public List<Notice> all() {
        return noticeRepository.findAllByOrderByCreatedAtDesc();
    }
}
