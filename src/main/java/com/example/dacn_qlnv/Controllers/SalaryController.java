package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Models.Salary;
import com.example.dacn_qlnv.Services.EmployeeService;
import com.example.dacn_qlnv.Services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listSalaries(Model model) {
        List<Salary> salaries = salaryService.getAllSalaries();
        model.addAttribute("salaries", salaries);
        return "salary/salaryList"; // trả về trang danh sách lương
    }

    @GetMapping("/add")
    public String showAddSalaryForm(Model model) {
        List<Employee> employees = employeeService.getActiveEmployees(); // Lấy danh sách nhân viên từ service
        model.addAttribute("employees", employees); // Thêm danh sách nhân viên vào mô hình
        model.addAttribute("salary", new Salary()); // Tạo một đối tượng Salary mới
        return "salary/addSalary"; // Trả về trang thêm lương
    }

    @PostMapping("/add")
    public String addSalary(@ModelAttribute Salary salary) {
        salaryService.calculateSalary(salary); // tính lương và lưu vào DB
        return "redirect:/salaries"; // chuyển hướng về danh sách lương
    }
    @GetMapping("/edit/{id}")
    public String showEditSalaryForm(@PathVariable Long id, Model model) {
        Salary salary = salaryService.getSalaryById(id);
        List<Employee> employees = employeeService.getActiveEmployees(); // Lấy danh sách nhân viên từ service
        model.addAttribute("employees", employees); // Thêm danh sách nhân viên vào mô hình
        model.addAttribute("salary", salary);
        return "salary/addSalary"; // Sử dụng lại form thêm để chỉnh sửa
    }

    @PostMapping("/edit")
    public String editSalary(@ModelAttribute Salary salary) {
        salaryService.updateSalary(salary);
        return "redirect:/salaries/SalaryList";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        return "redirect:/salaries/SalaryList";
    }
}

