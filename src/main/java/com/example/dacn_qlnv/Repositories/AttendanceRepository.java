package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee.id = :employeeId AND FUNCTION('MONTH', a.date) = :month AND FUNCTION('YEAR', a.date) = :year AND a.checkedIn = true")
    long countMonthlyAttendance(@Param("employeeId") Long employeeId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee.id = :employeeId AND FUNCTION('YEAR', a.date) = :year AND a.checkedIn = true")
    long countYearlyAttendance(@Param("employeeId") Long employeeId, @Param("year") int year);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee.id = :employeeId AND FUNCTION('MONTH', a.date) = :month AND FUNCTION('YEAR', a.date) = :year AND a.checkedIn = true")
    long countDaysWorkedInMonth(@Param("employeeId") Long employeeId, @Param("month") int month, @Param("year") int year);

    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}

