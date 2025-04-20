package com.example.demo.Service;

import com.example.demo.DTOs.StaffDTO;
import com.example.demo.Entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IStaff {
    Staff createStaff(StaffDTO staffDTO) throws Exception;
    List<Staff> getAll();
    Page<Staff> pageStaff(PageRequest pageRequest);
    Staff updateStaff(String id, StaffDTO staffDTO) throws Exception;
    Boolean existsByCode(String code);
    Staff findById(String id) throws Exception;
    void changeStatus(String id) throws Exception;

    void importExcel(MultipartFile file);
    ByteArrayInputStream exportExcel();
}
