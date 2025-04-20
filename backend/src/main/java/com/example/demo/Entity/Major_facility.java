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
@Table(name = "major_facility")
public class Major_facility extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "id_major", referencedColumnName = "id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "id_department_facility", referencedColumnName = "id")
    private Department_Facility department_facility;
}
