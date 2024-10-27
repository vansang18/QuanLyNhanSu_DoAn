package com.example.dacn_qlnv.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home/home";
    }
    @GetMapping("/chatting")
    public String index() {
        return "home/index"; // Trả về tên file không có phần mở rộng
    }
}
