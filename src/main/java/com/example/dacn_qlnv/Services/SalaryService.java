package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Salary;
import com.example.dacn_qlnv.Repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    // Phương thức tính lương cho nhân viên
    public Salary calculateSalary(Salary salary) {
        // Lấy các giá trị từ salary
        Double baseSalary = salary.getBaseSalary();
        Double overtime = salary.getOvertime() != null ? salary.getOvertime() : 0.0;
        Double bonus = salary.getBonus() != null ? salary.getBonus() : 0.0;
        Double deductions = salary.getDeductions() != null ? salary.getDeductions() : 0.0;

        // Tính tổng lương
        Double totalSalary = baseSalary + (overtime * (baseSalary / 160) * 1.5) + bonus - deductions;
        salary.setTotalSalary(totalSalary);

        // Tính lương thực nhận
        salary.setNetSalary(totalSalary); // Cập nhật theo yêu cầu, có thể có logic khấu trừ thêm

        // Lưu lương vào DB
        return salaryRepository.save(salary);
    }

    public List<Salary> getSalariesByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId); // Lấy lương theo nhân viên
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll(); // Lấy tất cả các bản ghi lương
    }

    public void saveSalary(Salary salary) {
        salaryRepository.save(salary);
    }

    public Salary getSalaryById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    public void updateSalary(Salary salary) {
        salaryRepository.save(salary);
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}
