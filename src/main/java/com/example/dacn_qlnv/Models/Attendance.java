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
@Table(name = "attendance")//cham cong
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "check_in_time")
    private Date checkInTime;

    @Column(name = "check_out_time")
    private Date checkOutTime;

    @Column(name = "total_hours")
    private Double totalHours;

    // Getters and setters
    // Constructors
}
