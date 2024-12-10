package com.example.dacn_qlnv.Controllers;

import com.example.dacn_qlnv.Services.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salary")
public class SalaryAPIController {

    private final SalaryService salaryService;

    public SalaryAPIController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/monthly")
    public ResponseEntity<Double> getMonthlySalary(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year) {

        Double salary = salaryService.calculateMonthlySalary(employeeId, month, year);
        return ResponseEntity.ok(salary);
    }
}

