package com.roomiesguard.backend.controller;

import com.roomiesguard.backend.entity.Notice;
import com.roomiesguard.backend.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    public Notice create(@RequestBody Notice notice) {
        return noticeService.create(notice);
    }

    @GetMapping
    public List<Notice> all() {
        return noticeService.all();
    }
}
