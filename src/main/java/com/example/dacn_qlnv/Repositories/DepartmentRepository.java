package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAll();
}
