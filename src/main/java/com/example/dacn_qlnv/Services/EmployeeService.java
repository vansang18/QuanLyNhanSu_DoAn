package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Phương thức để lấy danh sách nhân viên đang làm việc
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByIsResignedFalse();
    }

    public List<Employee> getResignedEmployees() {
        return employeeRepository.findByIsResignedTrue();
    }

    public void saveEmployee(Employee employee) {
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(encodedPassword);
        }
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
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
