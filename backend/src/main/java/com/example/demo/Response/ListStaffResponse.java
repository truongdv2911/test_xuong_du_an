package com.example.demo.Response;

import com.example.demo.Entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListStaffResponse {
    private List<Staff> staffs;
    private Integer totalPage;
}
