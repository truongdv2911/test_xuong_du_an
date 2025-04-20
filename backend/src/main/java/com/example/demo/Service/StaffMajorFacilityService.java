package com.example.demo.Service;

import com.example.demo.DTOs.Staff_major_facilityDTO;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import com.example.demo.Response.SMFResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StaffMajorFacilityService implements IStaffMajorFacility{
    private final StaffRepository staffRepository;
    private final MajorRepository majorRepository;
    private final FacilityRepository facilityRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentFacilityRepository departmentfacilityRepository;
    private final MajorFacilityRepository majorFacilityRepository;
    private final StaffMajorFacilityRepository staffMajorFacilityRepository;

    @Override
    public List<SMFResponse> getListSMF(String idStaff) throws Exception {
        try {
            return staffMajorFacilityRepository.getDetail(idStaff);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void createStaffMajorFacility(Staff_major_facilityDTO smfDTO) {
        try {
            // Validate entities trước khi tạo
            var staff = staffRepository.findById(smfDTO.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy staff với ID: " + smfDTO.getStaffId()));
            var facility = facilityRepository.findById(smfDTO.getIdFacility())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy facility với ID: " + smfDTO.getIdFacility()));
            var department = departmentRepository.findById(smfDTO.getIdDepart())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy department với ID: " + smfDTO.getIdDepart()));
            var major = majorRepository.findById(smfDTO.getIdMajor())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy major với ID: " + smfDTO.getIdMajor()));

            // Tạo các entity
            Department_Facility departmentFacility = departmentfacilityRepository.save(
                    new Department_Facility(null, 1, staff, facility, department));

            Major_facility majorFacility = majorFacilityRepository.save(
                    new Major_facility(null, 1, major, departmentFacility));

            staffMajorFacilityRepository.save(
                    new Staff_major_facility(null, 1, staff, majorFacility));

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException("Lỗi khi tạo StaffMajorFacility: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteStaffMajorFacility(String id) {
        Staff_major_facility staff_major_facility = staffMajorFacilityRepository.findById(id).orElseThrow(() -> new RuntimeException("Khong tim thay ID"));
        staff_major_facility.setStatus(0);
        staffMajorFacilityRepository.save(staff_major_facility);
    }

    @Override
    public List<Major> getListMajor() throws Exception {
        try {
            return majorRepository.findAll();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Department> getListDepartment() throws Exception {
        try {
            return departmentRepository.findAll();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Facility> getListFacility(String idStaff) throws Exception {
        try {
            return facilityRepository.getFaciWithIdStaff(idStaff);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
