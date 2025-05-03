package com.erp.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment {
    @Id
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateMiseEnService;

    @OneToMany(mappedBy = "equipment")
    private List<Piece> pieces;

    @OneToMany(mappedBy = "equipment")
    private List<Intervention> interventions;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;
    @OneToMany(mappedBy = "equipment")
    private List<ParameterType> parameterType ;
}

