package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "sign/login"; // Trả về trang đăng nhập
    }

//    @PostMapping("/login")
//    public String authenticate(@RequestParam String email, @RequestParam String password) {
//        if (loginService.authenticate(email, password)) {
//            // Đăng nhập thành công, chuyển hướng đến trang chính
//            return "redirect:/home";
//        }
//        // Đăng nhập thất bại, quay lại trang đăng nhập với thông báo lỗi
//        return "redirect:/login?error=true";
//    }
}
