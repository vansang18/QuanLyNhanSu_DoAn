package com.example.dacn_qlnv.Repositories;

import com.example.dacn_qlnv.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
