package com.erp.erp.dto;

import com.erp.erp.entity.Category;
import com.erp.erp.entity.Equipment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
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
public class PieceDTO {

    private String id;
    private String name;
    private Long category;
    private String sku;
    private BigDecimal price;
    private Long stockQuantity;
    private String description;
    private Date expiryDate;
    private Date  updatedAt;
    private Date createdAt;
    private String equipmentId;

}
