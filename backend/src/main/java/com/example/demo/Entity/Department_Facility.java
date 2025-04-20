package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department_facility")
public class Department_Facility extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "id_staff", referencedColumnName = "id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "id_facility", referencedColumnName = "id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "id_department", referencedColumnName = "id")
    private Department department;
}
