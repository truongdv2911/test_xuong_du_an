package com.example.demo.Service;

import com.example.demo.Config.ExcelHelper;
import com.example.demo.DTOs.StaffDTO;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaff{
    private final StaffRepository staffRepository;
    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;
    private final FacilityRepository facilityRepository;
    private final DepartmentFacilityRepository departmentfacilityRepository;
    private final MajorFacilityRepository majorFacilityRepository;
    private final StaffMajorFacilityRepository staffMajorFacilityRepository;
    private final ImportHistoryRepository importHistoryRepository;
    @Override
    @Transactional
    public Staff createStaff(StaffDTO staffDTO) throws Exception {
        try {
            if (staffRepository.existsByMa(staffDTO.getMa())){
                throw new Exception("Ma da ton tai");
            }
            return staffRepository.save(new Staff(
                    null,
                    staffDTO.getMa(),
                    staffDTO.getName(),
                    staffDTO.getFe(),
                    staffDTO.getFpt(),
                    staffDTO.getStatus()

            ));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    @Override
    public Page<Staff> pageStaff(PageRequest pageRequest) {
        return staffRepository.findStaff(pageRequest);
    }

    @Override
    @Transactional
    public Staff updateStaff(String id, StaffDTO staffDTO) throws Exception {
        try {
            staffRepository.findById(id).orElseThrow(()-> new RuntimeException("Khong tim thay id staff"));
            return staffRepository.save(new Staff(
                    id,
                    staffDTO.getMa(),
                    staffDTO.getName(),
                    staffDTO.getFe(),
                    staffDTO.getFpt(),
                    staffDTO.getStatus()
            ));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Boolean existsByCode(String code) {
        return staffRepository.existsByMa(code);
    }

    @Override
    public Staff findById(String id) throws Exception {
        try {
            return staffRepository.findById(id).orElseThrow(()->new RuntimeException("Khong tim thay id staff"));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void changeStatus(String id) throws Exception {
        try {
            Staff staff = staffRepository.findById(id).orElseThrow(()-> new RuntimeException("Khong tim thay ID staff"));
            if(staff.getStatus()==1){
                staff.setStatus(0);
                staffRepository.save(staff);
            }else{
                staff.setStatus(1);
                staffRepository.save(staff);
            }

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile file) {
        List<String> errorMessages = new ArrayList<>();
        int successCount = 0;
        try {
            List<StaffExcel> staffs = ExcelHelper.excelToNhanViens(file.getInputStream());
            for (StaffExcel staffExcel : staffs) {
                // Kiểm tra mã và email trùng lặp
                if (staffRepository.existsByMa(staffExcel.getStaffCode())) {
                    errorMessages.add("Mã nhân viên đã tồn tại.");
                    continue;
                }
                if (staffRepository.existsByFe(staffExcel.getAccountFe())) {
                    errorMessages.add("Tài khoản FE đã tồn tại.");
                    continue; // Chuyển đến nhân viên tiếp theo nếu email FE trùng
                }
                if (staffRepository.existsByFpt(staffExcel.getAccountFpt())) {
                    errorMessages.add("Tài khoản FPTđã tồn tại.");
                    continue;
                }

                String[] localName = staffExcel.getMajorFacilityDepart().split("-");
                if (localName.length == 3) {
                    try {
                        String idMajor = Optional.ofNullable(majorRepository.findByName(localName[0].trim()))
                                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy major: " + localName[0])).getId();

                        String idDepart = Optional.ofNullable(departmentRepository.findByName(localName[1].trim()))
                                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy department: " + localName[1])).getId();

                        String idFacility = Optional.ofNullable(facilityRepository.findByName(localName[2].trim()))
                                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy facility: " + localName[2])).getId();
                        // them staff
                        Staff staff = staffRepository.save(new Staff(null, staffExcel.getStaffCode(), staffExcel.getName(),
                                staffExcel.getAccountFe(), staffExcel.getAccountFpt(), 1));

                        //them smf
                        Facility facility = facilityRepository.findById(idFacility)
                                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy facility với ID: " + idFacility));
                        Department department = departmentRepository.findById(idDepart)
                                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy department với ID: " + idDepart));
                        Major major = majorRepository.findById(idMajor)
                                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy major với ID: " + idMajor));

                        // Tạo các entity
                        Department_Facility departmentFacility = departmentfacilityRepository.save(
                                new Department_Facility(null, 1, staff, facility, department));

                        Major_facility majorFacility = majorFacilityRepository.save(
                                new Major_facility(null, 1, major, departmentFacility));

                        staffMajorFacilityRepository.save(
                                new Staff_major_facility(null, 1, staff, majorFacility));
                        successCount++;
                    } catch (IllegalArgumentException e) {
                        errorMessages.add(e.getMessage());
                    }
                } else {
                    errorMessages.add("Lỗi định dạng Major-Facility-Depart: " + staffExcel.getMajorFacilityDepart());
                }
            }
            ImportHistory history = new ImportHistory();
            history.setImportTime(LocalDateTime.now());
            history.setSuccess_count(successCount);
            history.setTotal_record(staffs.size());
            history.setError_count(errorMessages.size());
            importHistoryRepository.save(history);

        }catch (Exception e) {
            errorMessages.add("Lỗi import file Excel: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream exportExcel() {
        try {
            List<StaffExcel> list = staffRepository.getMajorDepartmentFacilityNames();
            return ExcelHelper.exportStaff(list);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi export file Excel: " + e.getMessage());
        }
    }
}
