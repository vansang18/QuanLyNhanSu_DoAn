package com.example.dacn_qlnv.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AttendanceController {

    @GetMapping("/attendance")
    public String getAttendancePage() {
        return "attendance/attendance"; // Tự động tìm trong thư mục static
    }
}
