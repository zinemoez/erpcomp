package com.erp.erp.mappers;

import com.erp.erp.dto.DailyParameterDTO;
import com.erp.erp.entity.DailyParameter;
import com.erp.erp.entity.ParameterType;
import com.erp.erp.repository.ParameterTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyParameterMapper {

    private final ParameterTypeRepository parameterTypeRepository;

    // Convert DailyParameter entity to DailyParameterDTO
    public DailyParameterDTO toDTO(DailyParameter dailyParameter) {
        if (dailyParameter == null) {
            return null;
        }

        return DailyParameterDTO.builder()
                .id(dailyParameter.getId())
                .value(dailyParameter.getValue())
                .parameterType(dailyParameter.getParameterType() != null ? dailyParameter.getParameterType().getId() : null)
                .build();
    }

    // Convert DailyParameterDTO to DailyParameter entity
    public DailyParameter toEntity(DailyParameterDTO dto) {
        if (dto == null) {
            return null;
        }

        DailyParameter dailyParameter = new DailyParameter();
        dailyParameter.setDate(dto.getDate());
        dailyParameter.setValue(dto.getValue());

        if (dto.getParameterType() != null) {
            ParameterType parameterType = parameterTypeRepository.findById(dto.getParameterType())
                    .orElseThrow(() -> new IllegalArgumentException("ParameterType not found with id: " + dto.getParameterType()));
            dailyParameter.setParameterType(parameterType);
        }

        return dailyParameter;
    }
}

