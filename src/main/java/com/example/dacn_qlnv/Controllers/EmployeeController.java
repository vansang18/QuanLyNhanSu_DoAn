package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Services.DepartmentService;
import com.example.dacn_qlnv.Services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;




    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> activeEmployees = employeeService.getActiveEmployees();
        model.addAttribute("activeEmployees", activeEmployees);
        return "employees/employeeList";
    }
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departments); // Đổ danh sách phòng ban vào form
        return "employees/addEmployee";
    }
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees/list";
    }
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments); // Đổ danh sách phòng ban vào form
        return "employees/editEmployee";
    }
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute("employee") Employee employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/employees/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/list";
    }

    @GetMapping("/resigned")
    public String listResignedEmployees(Model model) {
        List<Employee> resignedEmployees = employeeService.getResignedEmployees();
        model.addAttribute("resignedEmployees", resignedEmployees);
        return "employees/resignedEmployeeList";
    }
}
