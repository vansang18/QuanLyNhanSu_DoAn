package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Attendance;
import com.example.dacn_qlnv.Repositories.AttendanceRepository;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceAPIController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    // Điểm danh hôm nay
    @PostMapping("/checkin")
    public ResponseEntity<String> checkIn(@RequestParam Long employeeId) {
        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        // Kiểm tra xem đã điểm danh chưa
        Optional<Attendance> existingAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);

        if (existingAttendance.isPresent()) {
            return ResponseEntity.badRequest().body("Bạn đã điểm danh hôm nay!");
        }

        // Thêm bản ghi mới
        Attendance attendance = new Attendance();
        attendance.setEmployee(employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!")));
        attendance.setDate(Date.valueOf(today));
        attendance.setCheckedIn(true);
        attendanceRepository.save(attendance);

        return ResponseEntity.ok("Điểm danh thành công!");
    }
    @GetMapping("/monthly")
    public ResponseEntity<Long> getMonthlyAttendance(@RequestParam Long employeeId, @RequestParam int month, @RequestParam int year) {
        long count = attendanceRepository.countMonthlyAttendance(employeeId, month, year);
        return ResponseEntity.ok(count);
    }

    // Tổng số ngày điểm danh trong năm
    @GetMapping("/yearly")
    public ResponseEntity<Long> getYearlyAttendance(@RequestParam Long employeeId, @RequestParam int year) {
        long count = attendanceRepository.countYearlyAttendance(employeeId, year);
        return ResponseEntity.ok(count);
    }
}

