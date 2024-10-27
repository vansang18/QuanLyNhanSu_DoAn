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

    @Column(name = "date", nullable = false) //Ngày hôm đó
    private Date date;

    @Column(name = "check_in_time")            //Giờ vào làm
    private Date checkInTime;

    @Column(name = "check_out_time")           //Giờ nghỉ
    private Date checkOutTime;

    @Column(name = "total_hours")               //Tổng giờ làm
    private Double totalHours;

    @Column(name = "checked_in", nullable = true)
    private Boolean checkedIn = false; // Đã checkin

    @Column(name = "checked_out", nullable = true)
    private Boolean checkedOut = false; // Đã checkout

    @Column(name = "has_permission", nullable = true) // Có phép
    private Boolean hasPermission = false; // true: có phép

    @Column(name = "no_permission", nullable = true) // Không phép
    private Boolean noPermission = false; // true: không phép


    @Column(name = "is_late", nullable = true)
    private Boolean isLate = false; // Trễ

    public void setEmployeeId(Long employeeId) {

    }

    public void setLate(boolean b) {

    }

    public void setAbsent(boolean b) {

    }

    // Getters and setters
    // Constructors
}
