package com.erp.erp.entity;

import com.erp.erp.enums.POSITION;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter@Setter
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date=new Date();
    private Double value;
    @ManyToOne
    @JoinColumn(name = "parameter_type_id")
    private ParameterType parameterType;
    private String observation;
}
