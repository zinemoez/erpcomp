package com.erp.erp.mappers;



import com.erp.erp.dto.ParameterTypeDTO;
import com.erp.erp.entity.DailyParameter;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.ParameterType;
import com.erp.erp.repository.DailyParameterRepository;
import com.erp.erp.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParameterTypeMapper {

    private final EquipmentRepository equipmentRepository;
    private final DailyParameterRepository dailyParameterRepository;

    // Convert Entity to DTO
    public ParameterTypeDTO toDTO(ParameterType parameterType) {
        if (parameterType == null) {
            return null;
        }

        ParameterTypeDTO dto = new ParameterTypeDTO();
        dto.setId(parameterType.getId());
        dto.setName(parameterType.getName());
        dto.setUnit(parameterType.getUnit());
        dto.setParamType(parameterType.getParamType());
        dto.setMin(parameterType.getMin());
        dto.setMax(parameterType.getMax());
        dto.setPosition(parameterType.getPosition());

        if (parameterType.getEquipment() != null) {
            dto.setEquipmentId(parameterType.getEquipment().getId());
        }

        if (parameterType.getDailyParameters() != null) {
            List<Long> dailyIds = parameterType.getDailyParameters()
                    .stream()
                    .map(DailyParameter::getId)
                    .collect(Collectors.toList());
            dto.setDailyParameterIds(dailyIds);
        }

        return dto;
    }

    // Convert DTO to Entity
    public ParameterType toEntity(ParameterTypeDTO dto) {
        if (dto == null) {
            return null;
        }

        ParameterType parameterType = new ParameterType();
        parameterType.setId(dto.getId());
        parameterType.setName(dto.getName());
        parameterType.setUnit(dto.getUnit());
        parameterType.setParamType(dto.getParamType());
        parameterType.setMin(dto.getMin());
        parameterType.setMax(dto.getMax());
        parameterType.setPosition(dto.getPosition());

        if (dto.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + dto.getEquipmentId()));
            parameterType.setEquipment(equipment);
        }

        if (dto.getDailyParameterIds() != null && !dto.getDailyParameterIds().isEmpty()) {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findAllById(dto.getDailyParameterIds());
            parameterType.setDailyParameters(dailyParameters);
        }

        return parameterType;
    }
}

