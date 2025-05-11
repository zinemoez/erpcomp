package com.erp.erp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentDTO {
    private String id;
    private String name;
    private String description;
    private Date dateMiseEnService;
    private String departmentId;
    private List<String> pieces;
    private List<Integer> interventions;
    private List<Long> parameterTypes;
}
