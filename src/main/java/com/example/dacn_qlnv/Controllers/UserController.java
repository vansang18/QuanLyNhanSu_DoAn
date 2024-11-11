package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/changePassword")
    public String changePasswordForm() {
        return "sign/change_password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String newPassword, HttpSession session, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        employeeService.changePassword(userDetails.getUsername(), newPassword);

        // Invalidate session
        session.invalidate();

        return "redirect:/login?passwordChanged";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "sign/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        employeeService.resetPassword(email);
        model.addAttribute("message", "A password reset link has been sent to your email.");
        return "sign/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token, Model model) {
        if (employeeService.validateResetToken(token)) {
            model.addAttribute("token", token);
            return "sign/reset-password";
        } else {
            model.addAttribute("message", "Invalid password reset token.");
            return "sign/login";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                Model model) {
        if (employeeService.validateResetToken(token)) {
            employeeService.updatePassword(token, password);
            model.addAttribute("message", "Password reset successfully. Please log in.");
            return "redirect:/login";
        } else {
            model.addAttribute("message", "Invalid password reset token.");
            return "sign/login";
        }
    }
    @GetMapping("/profile") // Đường dẫn đến trang thông tin cá nhân
    public String viewProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Employee> employeeOptional = employeeService.findByUsername(username);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                model.addAttribute("employee", employee);
            }
        }
        return "employees/profile"; // Trả về tên view
    }
}
