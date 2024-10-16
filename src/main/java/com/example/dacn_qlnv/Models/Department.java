package com.example.dacn_qlnv.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")//phong ban
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees;


}
