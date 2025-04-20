package com.example.demo.DTOs;

import com.example.demo.Entity.Staff;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    @UniqueField(entity = Staff.class, fieldName = "ma", message = "Ma da ton tai")
    private String ma;
    @NotBlank(message = "Khong de trong ten")
    private String name;
    @UniqueField(entity = Staff.class, fieldName = "fe", message = "Email fe da ton tai")
    @Pattern(regexp = "^[\\w.%+-]+@fe\\.edu\\.vn$", message = "Chỉ chấp nhận email @fe.edu.vn")
    private String fe;
    @UniqueField(entity = Staff.class, fieldName = "fpt", message = "Email FPT da ton tai")
    @Pattern(regexp = "^[\\w.%+-]+@fpt\\.edu\\.vn$", message = "Chỉ chấp nhận email @fpt.edu.vn")
    private String fpt;
    @NotNull
    private Integer status;
}
