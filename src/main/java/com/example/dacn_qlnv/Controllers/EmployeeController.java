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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
        List<Employee> allEmployees = employeeService.getAllEmployees();
        //List<Employee> employees = employeeService.searchEmployees(keyword);
        model.addAttribute("employees", allEmployees);
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
                              @RequestParam("file") MultipartFile file,
                              Model model) throws IOException {
        if (result.hasErrors()) {
            List<Department> departments = departmentService.getAllDepartments();
            model.addAttribute("departments", departments);
            return "employees/addEmployee";
        }
        employeeService.saveEmployee(employee, file);
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
                                 @RequestParam("file") MultipartFile file,
                                 Model model) throws IOException {
        if (result.hasErrors()) {
            List<Department> departments = departmentService.getAllDepartments();
            model.addAttribute("departments", departments);
            return "employees/editEmployee";
        }
        employeeService.updateEmployee(id, employee, file);
        return "redirect:/employees/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/list";
    }

    @GetMapping("/resign/{id}")
    public String resignEmployee(@PathVariable Long id) {
        // Lấy thông tin nhân viên theo ID
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            employee.setResigned(true); // Đánh dấu trạng thái nghỉ việc
            employee.setResignationDate(new Date()); // Ghi lại ngày nghỉ việc
            employeeService.saveEmployee(employee); // Lưu thông tin thay đổi
        }
        return "redirect:/employees/list"; // Quay lại danh sách nhân viên
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
