package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticate(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            return passwordEncoder.matches(password, employee.getPassword());
        }
        return false; // Nếu không tìm thấy nhân viên
    }
}
