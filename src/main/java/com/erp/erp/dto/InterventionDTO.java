package com.erp.erp.dto;


import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.Piece;
import com.erp.erp.entity.User;
import com.erp.erp.enums.Etat;
import com.erp.erp.enums.Priority;
import com.erp.erp.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterventionDTO {
    private Long id;
    private String title;
    private String equipmentId;
    private Long  approvedBy;
    private Long  createdBy;
    private Etat status;
    private Type type;
    private List<Long> staffIds;
    private String description;
    private List<String> pieces;
    private Date createdAt;
    private Date updatedAt;
    private Long updatedBy;
    private Priority priority;
}