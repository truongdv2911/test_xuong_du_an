package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffExcel {
    private String staffCode;
    private String name;
    private String accountFe;
    private String accountFpt;
    private String majorFacilityDepart;
}
