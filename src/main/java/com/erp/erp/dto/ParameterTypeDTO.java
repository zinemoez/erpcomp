package com.erp.erp.dto;

import com.erp.erp.entity.DailyParameter;
import com.erp.erp.entity.Equipment;
import com.erp.erp.enums.POSITION;
import com.erp.erp.enums.ParamTyPE;
import com.erp.erp.enums.Unity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterTypeDTO {
    private Long id;
    private String name;
    private Unity unit;
    private ParamTyPE paramType;
    private Double min;
    private Double max;
    private POSITION position;
    private List<Long> dailyParameterIds;
    private String equipmentId;
}
