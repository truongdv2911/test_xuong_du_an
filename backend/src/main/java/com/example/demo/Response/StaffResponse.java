package com.example.demo.Response;

import com.example.demo.Entity.BaseEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponse extends BaseEntity {
    private String ma;
    private String name;
    private String fe;
    private String fpt;
    private Integer status;
}
