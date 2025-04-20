package com.example.demo.Repository;

import com.example.demo.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, String> {
    Major existsByCode(String code);
    Major findByName(String name);

}
