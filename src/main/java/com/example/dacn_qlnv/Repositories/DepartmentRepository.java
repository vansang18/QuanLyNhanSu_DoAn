package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
}
