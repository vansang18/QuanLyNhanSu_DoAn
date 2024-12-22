package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/departments")
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
//    @GetMapping("/api/departments")
//    public List<Department> getAllDepartments() {
//        return departmentService.getAllDepartments();
//    }
    @GetMapping("/departments/list")
    public String listDepartments(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "department/departmentList"; // Tên của file HTML trong thư mục templates/department
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("department", new Department());
        return "departmentForm"; // Tạo view cho form thêm phòng ban
    }

    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/departments/departmentList"; // Quay lại danh sách phòng ban
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "departmentForm"; // Tạo view cho form sửa phòng ban
    }

    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute Department department) {
        departmentService.save(department);
        return "redirect:/departments/departmentList"; // Quay lại danh sách phòng ban
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteById(id);
        return "redirect:/departments/departmentList"; // Quay lại danh sách phòng ban
    }
}
