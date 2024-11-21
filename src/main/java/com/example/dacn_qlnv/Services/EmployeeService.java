package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Role;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import com.example.dacn_qlnv.Repositories.IEmployeeRepository;
import com.example.dacn_qlnv.Repositories.IRoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class EmployeeService implements UserDetailsService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private MailService mailService;


    // Phương thức để lấy danh sách nhân viên đang làm việc
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByIsResignedFalse();
    }

    public List<Employee> getResignedEmployees() {
        return employeeRepository.findByIsResignedTrue();
    }

    public void saveEmployee(Employee employee) {
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            existingEmployee.setHireDate(updatedEmployee.getHireDate());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            employeeRepository.save(existingEmployee);
        }
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public void setDefaultRole(String username) {
        employeeRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.EMPLOYEE.value));
                    employeeRepository.save(user);
                },
                () -> { throw new UsernameNotFoundException("User not found"); }
        );
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        var employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(employee.getUsername())
                .password(employee.getPassword())
                .authorities(employee.getAuthorities())
                .accountExpired(!employee.isAccountNonExpired())
                .accountLocked(!employee.isAccountNonLocked())
                .credentialsExpired(!employee.isCredentialsNonExpired())
                .disabled(!employee.isEnabled())
                .build();
    }

    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public Optional<Employee> findByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username);
    }

    // Thay đổi mật khẩu người dùng.
    public void changePassword(String username, String newPassword) {
        Employee user = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        employeeRepository.save(user);
    }

    // Đặt lại mật khẩu người dùng.
    public void resetPassword(String email) {
        Optional<Employee> userOptional = employeeRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Employee employee = userOptional.get();
            String token = UUID.randomUUID().toString();
            employee.setResetToken(token);
            employeeRepository.save(employee);

            String resetLink = "http://localhost:8080/reset-password?token=" + token;
            String message = "Click the link below to reset your password:\n" + resetLink;
            mailService.sendEmail(employee.getEmail(), "Password Reset Request", message);
        }
    }

    // Xác nhận token đặt lại mật khẩu.
    public boolean validateResetToken(String token) {
        Optional<Employee> userOptional = employeeRepository.findByResetToken(token);
        return userOptional.isPresent();
    }

    // Cập nhật mật khẩu người dùng dựa trên token đặt lại mật khẩu.
    public void updatePassword(String token, String newPassword) {
        Optional<Employee> userOptional = employeeRepository.findByResetToken(token);
        if (userOptional.isPresent()) {
            Employee employee = userOptional.get();
            employee.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            employee.setResetToken(null);
            employeeRepository.save(employee);
        }
    }

}
