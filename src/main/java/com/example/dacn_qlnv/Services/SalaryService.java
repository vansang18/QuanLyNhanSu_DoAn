package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Employee;
import com.example.dacn_qlnv.Repositories.AttendanceRepository;
import com.example.dacn_qlnv.Repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    public Double calculateMonthlySalary(Long employeeId, int month, int year) {
        // Lấy thông tin nhân viên
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        // Đếm số ngày đi làm trong tháng
        long daysWorked = attendanceRepository.countDaysWorkedInMonth(employeeId, month, year);

        // Tính lương = số ngày đi làm * lương cơ bản
        return daysWorked * employee.getSalary();
    }
}

