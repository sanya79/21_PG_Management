package com.roomiesguard.backend.config;

import com.roomiesguard.backend.entity.*;
import com.roomiesguard.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seed(TenantRepository tenantRepository,
                           RoomRepository roomRepository,
                           BedRepository bedRepository,
                           VisitorLogRepository visitorLogRepository,
                           RentRecordRepository rentRecordRepository,
                           ComplaintRepository complaintRepository,
                           NoticeRepository noticeRepository) {
        return args -> {
            if (tenantRepository.count() > 0) {
                return;
            }

            Room r11 = room("r-1-1", 1, "101");
            Room r12 = room("r-1-2", 1, "102");
            Room r13 = room("r-1-3", 1, "103");
            roomRepository.saveAll(List.of(r11, r12, r13));

            Bed b111 = bed("r-1-1-b1", "r-1-1", "B1", "t1");
            Bed b112 = bed("r-1-1-b2", "r-1-1", "B2", null);
            Bed b121 = bed("r-1-2-b1", "r-1-2", "B1", "t2");
            Bed b122 = bed("r-1-2-b2", "r-1-2", "B2", null);
            Bed b131 = bed("r-1-3-b1", "r-1-3", "B1", "t3");
            bedRepository.saveAll(List.of(b111, b112, b121, b122, b131));

            Tenant t1 = tenant("t1", "Aarav Sharma", "9876543210", "AAD-1234", "r-1-1", "r-1-1-b1", "2025-01-15");
            Tenant t2 = tenant("t2", "Priya Verma", "9876501234", "AAD-5678", "r-1-2", "r-1-2-b1", "2025-02-10");
            Tenant t3 = tenant("t3", "Rohan Mehta", "9988776655", "AAD-9012", "r-1-3", "r-1-3-b1", "2025-03-05");
            tenantRepository.saveAll(List.of(t1, t2, t3));

            RentRecord rent1 = rent("rent-1", "t1", "2026-05", 8500, "2026-05-05", "paid", null);
            RentRecord rent2 = rent("rent-2", "t2", "2026-05", 8500, "2026-05-05", "submitted", "upi_2025.png");
            RentRecord rent3 = rent("rent-3", "t3", "2026-05", 8500, "2026-05-05", "pending", null);
            rentRecordRepository.saveAll(List.of(rent1, rent2, rent3));

            Complaint c1 = complaint("c-1", "t2", "Geyser not working", "Hot water unavailable since morning", "In Progress", OffsetDateTime.now().minusDays(2));
            Complaint c2 = complaint("c-2", "t3", "WiFi slow", "Internet very slow in evenings", "Open", OffsetDateTime.now().minusDays(1));
            complaintRepository.saveAll(List.of(c1, c2));

            Notice n1 = notice("n-1", "Water tank cleaning", "Water supply will be interrupted on Sunday 9-11 AM.", OffsetDateTime.now().minusDays(3));
            Notice n2 = notice("n-2", "Rent reminder", "Kindly clear your dues before the 5th of every month.", OffsetDateTime.now().minusDays(1));
            noticeRepository.saveAll(List.of(n1, n2));

            VisitorLog v1 = visitor("v-1", "Karan Singh", "9000011111", "Friend visit", "t1", "approved", OffsetDateTime.now().minusHours(1), null);
            VisitorLog v2 = visitor("v-2", "Delivery - Zomato", "9000022222", "Food delivery", "t2", "pending", OffsetDateTime.now().minusMinutes(15), null);
            visitorLogRepository.saveAll(List.of(v1, v2));
        };
    }

    private Tenant tenant(String id, String name, String phone, String kycId, String roomId, String bedId, String joinedAt) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setName(name);
        tenant.setPhone(phone);
        tenant.setKycId(kycId);
        tenant.setRoomId(roomId);
        tenant.setBedId(bedId);
        tenant.setJoinedAt(joinedAt);
        return tenant;
    }

    private Room room(String id, int floor, String number) {
        Room room = new Room();
        room.setId(id);
        room.setFloor(floor);
        room.setNumber(number);
        return room;
    }

    private Bed bed(String id, String roomId, String label, String tenantId) {
        Bed bed = new Bed();
        bed.setId(id);
        bed.setRoomId(roomId);
        bed.setLabel(label);
        bed.setTenantId(tenantId);
        return bed;
    }

    private RentRecord rent(String id, String tenantId, String month, Integer amount, String dueDate, String status, String proof) {
        RentRecord rentRecord = new RentRecord();
        rentRecord.setId(id);
        rentRecord.setTenantId(tenantId);
        rentRecord.setMonth(month);
        rentRecord.setAmount(amount);
        rentRecord.setDueDate(dueDate);
        rentRecord.setStatus(status);
        rentRecord.setProof(proof);
        return rentRecord;
    }

    private Complaint complaint(String id, String tenantId, String title, String description, String status, OffsetDateTime createdAt) {
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setTenantId(tenantId);
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setStatus(status);
        complaint.setCreatedAt(createdAt);
        return complaint;
    }

    private Notice notice(String id, String title, String body, OffsetDateTime createdAt) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setTitle(title);
        notice.setBody(body);
        notice.setCreatedAt(createdAt);
        return notice;
    }

    private VisitorLog visitor(String id, String visitorName, String phone, String purpose, String tenantId, String status, OffsetDateTime timeIn, OffsetDateTime timeOut) {
        VisitorLog visitorLog = new VisitorLog();
        visitorLog.setId(id);
        visitorLog.setVisitorName(visitorName);
        visitorLog.setPhone(phone);
        visitorLog.setPurpose(purpose);
        visitorLog.setTenantId(tenantId);
        visitorLog.setStatus(status);
        visitorLog.setTimeIn(timeIn);
        visitorLog.setTimeOut(timeOut);
        visitorLog.setCreatedAt(timeIn);
        return visitorLog;
    }
}
