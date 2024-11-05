package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.IEmployeeRepository;
import com.example.dacn_qlnv.Services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final IEmployeeRepository employeeService;
    private final PasswordEncoder passwordEncoder; // Sử dụng PasswordEncoder để so sánh mật khẩu

    @GetMapping("/login")
    public String login(Model model) {
        return "sign/login"; // Đảm bảo đường dẫn đúng
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model) {
        // Kiểm tra xem username có tồn tại không
        Optional<Employee> optionalEmployee = employeeService.findByUsername(username);

        if (!optionalEmployee.isPresent()) {
            model.addAttribute("error", "Tên đăng nhập không tồn tại!");
            return "sign/login"; // Trả về trang đăng nhập
        }

        Employee employee = optionalEmployee.get(); // Lấy đối tượng Employee từ Optional

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, employee.getPassword())) {
            model.addAttribute("error", "Mật khẩu không chính xác!");
            return "sign/login"; // Trả về trang đăng nhập
        }

        // Nếu đăng nhập thành công, chuyển hướng đến trang chính
        return "redirect:/home"; // Chuyển hướng đến trang chính sau khi đăng nhập
    }
}
