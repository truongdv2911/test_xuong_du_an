package com.example.demo.Controller;

import com.example.demo.DTOs.Staff_major_facilityDTO;
import com.example.demo.Entity.Staff_major_facility;
import com.example.demo.Repository.StaffMajorFacilityRepository;
import com.example.demo.Response.MessageResponse;
import com.example.demo.Service.StaffMajorFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/smf")
@RequiredArgsConstructor
public class SMFController {
    private final StaffMajorFacilityService staffMajorFacilityService;

    @GetMapping("/getSMF/{id}")
    public ResponseEntity<?> getSMFById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(staffMajorFacilityService.getListSMF(id));
    }

    @GetMapping("/getMajor")
    public ResponseEntity<?> getListMajor() throws Exception {
        return ResponseEntity.ok(staffMajorFacilityService.getListMajor());
    }

    @GetMapping("/getDepartment")
    public ResponseEntity<?> getListDepartment() throws Exception {
        return ResponseEntity.ok(staffMajorFacilityService.getListDepartment());
    }

    @GetMapping("/getFacility/{id}")
    public ResponseEntity<?> getListFacility(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(staffMajorFacilityService.getListFacility(id));
    }

    @PostMapping("/createSMF")
    public ResponseEntity<?> createSMF(@RequestBody Staff_major_facilityDTO staffMajorFacilityDTO) throws Exception {
        try {
            staffMajorFacilityService.createStaffMajorFacility(staffMajorFacilityDTO);
            return ResponseEntity.ok(new MessageResponse("Them thanh cong"));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/deleteSMF/{id}")
    public ResponseEntity<?> deleteSMFById(@PathVariable String id) throws Exception {
        staffMajorFacilityService.deleteStaffMajorFacility(id);
        return ResponseEntity.ok(new MessageResponse("Xoa thanh cong"));
    }
}
