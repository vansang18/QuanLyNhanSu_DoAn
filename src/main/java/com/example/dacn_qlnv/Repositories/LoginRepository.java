package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Employee, Long> {
    Optional<Employee>findByEmail(String email);
    Optional<Employee>findByUsername(String username);
}
