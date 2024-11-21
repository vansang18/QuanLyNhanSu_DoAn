package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Phương thức để tìm nhân viên đang làm việc

    List<Employee> findByIsResignedFalse();
    List<Employee> findByIsResignedTrue();
    // Tìm kiếm danh sách nhân viên
    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.email) LIKE %:keyword% OR " +
            "LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE %:keyword% OR " +
            "e.phoneNumber LIKE %:keyword%")
    List<Employee> searchByKeyword(@Param("keyword") String keyword);
}
