package com.example.demo.DTOs;

import com.example.demo.Entity.Major_facility;
import com.example.demo.Entity.Staff;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Staff_major_facilityDTO {
    @NotNull
    private Integer status;
    @NotNull
    private String staffId;
    @NotNull
    private String idFacility;
    @NotNull
    private String idDepart;
    @NotNull
    private String idMajor;
}
