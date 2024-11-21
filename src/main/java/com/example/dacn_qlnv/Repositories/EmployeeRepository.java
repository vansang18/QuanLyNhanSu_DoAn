package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Phương thức để tìm nhân viên đang làm việc

    List<Employee> findByIsResignedFalse();
    List<Employee> findByIsResignedTrue();
    Employee findByEmail(String email);
    Employee findByUsername(String username);
}
