package com.erp.erp.mappers;

import com.erp.erp.dto.DailyParameterDTO;
import com.erp.erp.entity.DailyParameter;
import com.erp.erp.entity.ParameterType;
import com.erp.erp.repository.ParameterTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyParameterMapper {

    private final ParameterTypeRepository parameterTypeRepository;
    private final ParameterTypeMapper parameterTypeMapper;

    // Convertir DailyParameter (Entity) → DailyParameterDTO
    public DailyParameterDTO toDTO(DailyParameter dailyParameter) {
        if (dailyParameter == null) {
            return null;
        }

        return DailyParameterDTO.builder()
                .value(dailyParameter.getValue())
                .date(dailyParameter.getDate())
                .observation(dailyParameter.getObservation())
                .parameterType(parameterTypeMapper.toDTO(dailyParameter.getParameterType()))
                .build();
    }

    // Convertir DailyParameterDTO → DailyParameter (Entity)
    public DailyParameter toEntity(DailyParameterDTO dto) {
        if (dto == null) {
            return null;
        }

        DailyParameter dailyParameter = new DailyParameter();
        dailyParameter.setValue(dto.getValue());
        dailyParameter.setObservation(dto.getObservation());

        dailyParameter.setDate(dto.getDate() != null ? dto.getDate() : new java.util.Date());

        if (dto.getParameterType() != null && dto.getParameterType().getId() != null) {
            ParameterType parameterType = parameterTypeRepository
                    .findById(dto.getParameterType().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ParameterType not found with ID: " + dto.getParameterType().getId()));
            dailyParameter.setParameterType(parameterType);
        }

        return dailyParameter;
    }
}
