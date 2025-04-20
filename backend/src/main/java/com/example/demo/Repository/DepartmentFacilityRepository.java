package com.example.demo.Repository;

import com.example.demo.Entity.Department_Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentFacilityRepository extends JpaRepository<Department_Facility, String> {
}
