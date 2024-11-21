package com.example.dacn_qlnv.Services;

import com.example.dacn_qlnv.Models.Department;
import com.example.dacn_qlnv.Repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        Optional<Department> result = departmentRepository.findById(id);
        return result.orElse(null);
    }

    public void save(Department department) {
        departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
}
