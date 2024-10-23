package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByEmployeeId(Long employeeId); // Tìm kiếm lương theo nhân viên
}

