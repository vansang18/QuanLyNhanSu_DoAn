package com.example.dacn_qlnv.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SalaryController {

    // Ánh xạ URL /salary tới giao diện salary.html
    @GetMapping("/salary")
    public String getSalaryPage() {
        return "salary/salary"; // Tên file HTML trong thư mục static
    }
}
