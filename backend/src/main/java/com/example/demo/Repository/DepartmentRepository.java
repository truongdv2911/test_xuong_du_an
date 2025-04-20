package com.example.demo.Repository;

import com.example.demo.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    Department existsByCode(String code);
    Department findByName(String name);
}
