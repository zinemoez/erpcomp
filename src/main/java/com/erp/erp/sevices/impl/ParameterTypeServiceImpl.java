package com.erp.erp.sevices.impl;
import com.erp.erp.dto.ParameterTypeDTO;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.ParameterType;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.ParameterTypeRepository;
import com.erp.erp.sevices.serv.IParameterTypeService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.erp.erp.mappers.ParameterTypeMapper; // Import the mapper
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParameterTypeServiceImpl implements IParameterTypeService {

    private final ParameterTypeRepository parameterTypeRepository;
    private final ParameterTypeMapper parameterTypeMapper;
    private final EquipmentRepository equipmentRepository;// Inject the mapper

    @Override
    public List<ParameterTypeDTO> getAll() {
        return parameterTypeRepository.findAll().stream()
                .map(parameterTypeMapper::toDTO) // Use parameterTypeMapper to convert to DTO
                .collect(Collectors.toList());
    }

    @Override
    public ParameterTypeDTO getById(Long id) {
        ParameterType parameterType = parameterTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ParameterType not found with ID: " + id));
        return parameterTypeMapper.toDTO(parameterType);  // Use parameterTypeMapper to convert to DTO
    }

    @Override
    public ParameterTypeDTO save(ParameterTypeDTO dto) {
        ParameterType entity = parameterTypeMapper.toEntity(dto);  // Use parameterTypeMapper to convert to entity
        parameterTypeRepository.save(entity);
        return dto;
    }

    @Override
    public List<ParameterTypeDTO> getByEquipmentId(String id) {
        try {
            List<ParameterType> parameterTypes = parameterTypeRepository.findByEquipmentId(id);

            return parameterTypes.stream()
                    .map(parameterTypeMapper::toDTO)  // Use parameterTypeMapper to convert to DTO
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error while fetching ParameterTypes for Equipment ID: " + id + " - " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<ParameterTypeDTO> getByDepartmentId(String id) {
        try {
            List<ParameterType> parameterTypes = parameterTypeRepository.findByDepartmentId(id);

            return parameterTypes.stream()
                    .map(parameterTypeMapper::toDTO)  // Use parameterTypeMapper to convert to DTO
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error while fetching ParameterTypes for department ID: " + id + " - " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(Long id) {
        parameterTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ParameterType not found with ID: " + id));
        parameterTypeRepository.deleteById(id);
    }

    @Override
    public ParameterTypeDTO updateParameterType(Long id, ParameterTypeDTO parameterTypeDTO) {
        ParameterType parameterType = parameterTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ParameterType not found with ID: " + id));
        parameterType.setId(id);
        parameterType.setName(parameterTypeDTO.getName());
        parameterType.setMin(parameterTypeDTO.getMin());
        parameterType.setMax(parameterTypeDTO.getMax());
        parameterType.setPosition(parameterTypeDTO.getPosition());
        parameterType.setUnit(parameterTypeDTO.getUnit());
        if ( parameterTypeDTO.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById( parameterTypeDTO.getEquipmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + parameterTypeDTO.getEquipmentId()));
            parameterType.setEquipment(equipment);
        }
        parameterType.setParamType(parameterTypeDTO.getParamType());
        parameterTypeRepository.save(parameterType);
        return parameterTypeDTO;
    }


    }

