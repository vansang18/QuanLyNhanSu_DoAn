package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    List<Employee> findByIsResignedFalse();
    List<Employee> findByIsResignedTrue();
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByResetToken(String resetToken);
    @Query("SELECT e FROM Employee e WHERE " +
            "CAST(e.id AS string) LIKE %:keyword% OR " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "e.phoneNumber LIKE CONCAT('%', :keyword, '%')")
    List<Employee> searchByKeyword(@Param("keyword") String keyword);
}
