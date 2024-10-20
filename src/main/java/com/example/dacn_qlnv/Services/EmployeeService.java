package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Phương thức để lấy danh sách nhân viên đang làm việc
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByIsResignedFalse();
    }

    public List<Employee> getResignedEmployees() {
        return employeeRepository.findByIsResignedTrue();
    }

    public void saveEmployee(Employee employee) {
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
            employeeRepository.save(existingEmployee);
        }

    }
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
