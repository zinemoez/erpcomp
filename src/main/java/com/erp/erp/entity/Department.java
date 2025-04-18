package com.erp.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Equipment> equipments;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    private String description;

    @OneToMany(mappedBy = "department")
    private List<User> users;

}


