package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Attendance;
import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.AttendanceRepository;
import com.example.dacn_qlnv.Repositories.DepartmentRepository;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final DepartmentRepository departmentRepository;

    // Thống kê lương từ cao đến thấp (Top 5)
    public List<Employee> getTopSalaries() {
        return employeeRepository.findAll().stream()
                .filter(employee -> !employee.isResigned())  // Loại nhân viên đã nghỉ việc
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())  // Sắp xếp theo lương giảm dần
                .limit(5)  // Lấy tối đa 5 nhân viên
                .collect(Collectors.toList());
    }

    // Thống kê số ngày điểm danh từ cao đến thấp (Top 5)
    public Map<Employee, Long> getTopAttendanceDays() {
        List<Attendance> attendanceList = attendanceRepository.findAll();

        // Gom nhóm theo nhân viên và đếm số ngày điểm danh
        Map<Employee, Long> attendanceMap = attendanceList.stream()
                .filter(a -> a.getCheckedIn() && !a.getEmployee().isResigned())  // Lọc nhân viên chưa nghỉ
                .collect(Collectors.groupingBy(Attendance::getEmployee, Collectors.counting()));

        // Sắp xếp và giới hạn 5 nhân viên điểm danh nhiều nhất
        return attendanceMap.entrySet().stream()
                .sorted(Map.Entry.<Employee, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,   // Employee
                        Map.Entry::getValue, // Số ngày điểm danh
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // Thống kê số lượng nhân viên theo phòng ban
    public Map<String, Long> getEmployeeCountByDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();

        // Đếm số lượng nhân viên trong mỗi phòng ban
        return departments.stream()
                .collect(Collectors.toMap(
                        Department::getName,
                        dept -> employees.stream()
                                .filter(emp -> emp.getDepartment().getId().equals(dept.getId()) && !emp.isResigned())
                                .count()
                ));
    }
}
