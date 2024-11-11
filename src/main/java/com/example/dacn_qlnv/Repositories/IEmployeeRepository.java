package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
