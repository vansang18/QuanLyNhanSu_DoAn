package com.example.dacn_qlnv.Models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với bảng Employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Ngày điểm danh
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    // Giờ vào làm
    @Column(name = "check_in_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkInTime;

    // Giờ nghỉ làm
    @Column(name = "check_out_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOutTime;

    // Tổng số giờ làm việc
    @Column(name = "total_hours")
    private Double totalHours;

    // Trạng thái đã điểm danh
    @Column(name = "checked_in", nullable = false)
    private Boolean checkedIn = false;

    // Trạng thái đã nghỉ làm
    @Column(name = "checked_out", nullable = false)
    private Boolean checkedOut = false;

    // Có phép
    @Column(name = "has_permission", nullable = true)
    private Boolean hasPermission = false;

    // Không phép
    @Column(name = "no_permission", nullable = true)
    private Boolean noPermission = false;

    // Có trễ giờ
    @Column(name = "is_late", nullable = true)
    private Boolean isLate = false;

    // Phương thức để tính tổng số giờ làm việc
    public void calculateTotalHours() {
        if (checkInTime != null && checkOutTime != null) {
            long duration = checkOutTime.getTime() - checkInTime.getTime();
            this.totalHours = duration / (1000.0 * 60.0 * 60.0); // Chuyển sang giờ
        } else {
            this.totalHours = 0.0;
        }
    }
}
