package com.example.dacn_qlnv.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
    private String month; // Tháng tính lương, ví dụ: "2024-10"

    @Column(name = "base_salary", nullable = false)
    private Double baseSalary; // Lương cơ bản

    @Column(name = "overtime")
    private Double overtime; // Giờ làm thêm

    @Column(name = "bonus")
    private Double bonus; // Thưởng (nếu có)

    @Column(name = "deductions")
    private Double deductions; // Khấu trừ (bảo hiểm, thuế, v.v.)

    @Column(name = "total_salary", nullable = false)
    private Double totalSalary; // Tổng lương trước khi khấu trừ

    @Column(name = "net_salary", nullable = false)
    private Double netSalary; // Lương thực nhận sau khi khấu trừ

    @Column(name = "payment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate; // Ngày thanh toán lương
}
