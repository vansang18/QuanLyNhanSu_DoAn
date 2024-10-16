package com.example.dacn_qlnv.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salary")//luong lau
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "month", nullable = false)
    private String month;

    @Column(name = "base_salary")
    private Double baseSalary;

    @Column(name = "overtime")
    private Double overtime;

    @Column(name = "total_salary")
    private Double totalSalary;

}
