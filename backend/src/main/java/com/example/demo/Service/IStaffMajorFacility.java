package com.example.demo.Service;

import com.example.demo.DTOs.Staff_major_facilityDTO;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Facility;
import com.example.demo.Entity.Major;
import com.example.demo.Entity.Staff_major_facility;
import com.example.demo.Response.SMFResponse;

import java.util.List;

public interface IStaffMajorFacility {
    List<SMFResponse> getListSMF(String idStaff) throws Exception;
    void createStaffMajorFacility(Staff_major_facilityDTO staff_major_facilityDTO) throws Exception;
    void deleteStaffMajorFacility(String id);

    List<Major> getListMajor() throws Exception;
    List<Department> getListDepartment() throws Exception;
    List<Facility> getListFacility(String id) throws Exception;
}
