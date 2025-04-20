package com.example.demo.Repository;

import com.example.demo.Entity.Staff_major_facility;
import com.example.demo.Response.SMFResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffMajorFacilityRepository extends JpaRepository<Staff_major_facility, String> {

    @Query("select s from Staff_major_facility s where s.status = 1")
    List<Staff_major_facility> getAllBySta();

    @Query("""
    SELECT new com.example.demo.Response.SMFResponse(
       smf.id, f.name, d.name, m.name
    )
    FROM Facility f
    JOIN Department_Facility df ON f.id = df.facility.id
    JOIN Department d ON df.department.id = d.id
    JOIN Major_facility mf ON df.id = mf.department_facility.id
    JOIN Major m ON mf.major.id = m.id
    JOIN Staff_major_facility smf ON smf.major_facility.id = mf.id
    WHERE smf.staff.id = :staffId AND smf.status = 1
""")
    List<SMFResponse> getDetail(@Param("staffId") String staffId);
}
