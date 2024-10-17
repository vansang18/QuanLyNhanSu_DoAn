package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/api/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
