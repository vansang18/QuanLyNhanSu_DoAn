package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/me")
    public ResponseEntity<Employee> getCurrentUser() {
        // Lấy thông tin người dùng từ Spring Security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Tìm thông tin nhân viên dựa trên username
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin nhân viên!"));

        return ResponseEntity.ok(employee);
    }
}
