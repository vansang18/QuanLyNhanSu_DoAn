package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> activeEmployees = employeeService.getActiveEmployees();
        model.addAttribute("activeEmployees", activeEmployees);
        return "employees/employeeList";
    }

    @GetMapping("/resigned")
    public String listResignedEmployees(Model model) {
        List<Employee> resignedEmployees = employeeService.getResignedEmployees();
        model.addAttribute("resignedEmployees", resignedEmployees);
        return "employees/resignedEmployeeList";
    }
}
