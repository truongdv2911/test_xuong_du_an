package com.example.demo.Repository;

import com.example.demo.Entity.Staff;
import com.example.demo.Entity.StaffExcel;
import com.example.demo.Response.StaffResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, String> {
    Boolean existsByMa(String code);
    Boolean existsByFe(String fe);
    Boolean existsByFpt(String fpt);

    @Query("select s from Staff s")
    Page<Staff> findStaff(PageRequest pageRequest);

    @Query("SELECT new com.example.demo.Entity.StaffExcel(" +
            "s.ma, s.name, s.fe, s.fpt, " +
            "CONCAT(m.name, '-', d.name, '-', f.name)) " +
            "FROM Staff s " +
            "JOIN Staff_major_facility smf on s.id like smf.staff.id " +
            "JOIN Major_facility mf on smf.major_facility.id like mf.id " +
            "JOIN Major m on mf.major.id like m.id " +
            "JOIN Department_Facility df on mf.department_facility.id like df.id " +
            "JOIN Department d on df.department.id like d.id " +
            "JOIN Facility f on df.facility.id like f.id")
    List<StaffExcel> getMajorDepartmentFacilityNames();
}
