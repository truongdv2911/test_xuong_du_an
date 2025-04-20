package com.example.demo.Controller;

import com.example.demo.DTOs.StaffDTO;
import com.example.demo.Entity.ImportHistory;
import com.example.demo.Entity.Staff;
import com.example.demo.Repository.ImportHistoryRepository;
import com.example.demo.Response.ListStaffResponse;
import com.example.demo.Response.MessageResponse;
import com.example.demo.Service.StaffService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    private final ImportHistoryRepository importHistoryRepository;

    @PostMapping("/createStaff")
    public ResponseEntity<?> createStaff(@Valid @RequestBody StaffDTO staffDTO,
                                         BindingResult result) throws Exception {
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            return ResponseEntity.ok(staffService.createStaff(staffDTO));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/updateStaff/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable String id, @RequestBody StaffDTO staffDTO) throws Exception {
        try {
            return ResponseEntity.ok(staffService.updateStaff(id,staffDTO));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getListStaff")
    public ResponseEntity<?> pageStaff(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("size") Integer size
    ){
        PageRequest pageRequest = PageRequest.of(pageNo, size, Sort.by("createAt").descending());
        Page<Staff> staffPage = staffService.pageStaff(pageRequest);

        int totalPage = staffPage.getTotalPages();
        List<Staff> staffs =staffPage.getContent();
        return ResponseEntity.ok(new ListStaffResponse(staffs,totalPage));
    }

    @DeleteMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String id) throws Exception {
        try {
            staffService.changeStatus(id);
            MessageResponse messageResponse = new MessageResponse("Thay doi trang thai thanh cong");
            return ResponseEntity.ok(messageResponse);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/getOneStaff/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) throws Exception {
        try {
            return ResponseEntity.ok(staffService.findById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        staffService.importExcel(file);
        return ResponseEntity.ok("Import thành công");
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=nhanvien.xlsx");
        staffService.exportExcel().transferTo(response.getOutputStream());
    }

    @GetMapping("/import-history")
    public ResponseEntity<?> getAllHistory() {
        Sort sort = Sort.by(Sort.Direction.DESC, "importTime");
        return ResponseEntity.ok(importHistoryRepository.findAll(sort));
    }
}
