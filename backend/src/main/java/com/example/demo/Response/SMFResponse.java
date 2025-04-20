package com.example.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SMFResponse {
    private String id;
    private String nameFacility;
    private String nameDepartment;
    private String nameMajor;
}
