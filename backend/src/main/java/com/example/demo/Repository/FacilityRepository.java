package com.example.demo.Repository;

import com.example.demo.Entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, String> {
    Facility findByName(String name);

    @Query("SELECT f FROM Facility f " +
            "WHERE f.id not in (SELECT df.facility.id FROM Department_Facility df " +
            "join Major_facility mf on df.id like mf.department_facility.id " +
            "join Staff_major_facility smf on mf.id like smf.major_facility.id WHERE smf.staff.id = :idStaff and smf.status = 1 )")
    List<Facility> getFaciWithIdStaff(@Param("idStaff") String staffId);
}
