package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Services.DepartmentService;
import com.example.dacn_qlnv.Services.EmployeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public String listEmployees(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Employee> activeEmployees = employeeService.getActiveEmployees();
        //List<Employee> employees = employeeService.searchEmployees(keyword);
        model.addAttribute("activeEmployees", activeEmployees);
        model.addAttribute("keyword", keyword);
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
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employee,
                              BindingResult result,
                              Model model) {
       /* employeeService.saveEmployee(employee);
        return "redirect:/employees/list";*/
        if (result.hasErrors()) {
            List<Department> departments = departmentService.getAllDepartments();
            model.addAttribute("departments", departments);
            return "employees/addEmployee"; // Quay lại form thêm nhân viên
        }
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
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute("employee") @Valid Employee employee,
                                 BindingResult result,
                                 Model model) {
       /* employeeService.updateEmployee(id, employee);
        return "redirect:/employees/list";*/
        if (result.hasErrors()) {
            List<Department> departments = departmentService.getAllDepartments();
            model.addAttribute("departments", departments);
            model.addAttribute("employee", employee);
            return "employees/editEmployee"; // Quay lại form chỉnh sửa nhân viên
        }
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
    /*@ModelAttribute
    public void addEmployeeToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Employee> employeeOptional = employeeService.findByUsername(username);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                model.addAttribute("employee", employee);
            }
        }
    }*/

}
