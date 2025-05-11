package com.erp.erp.mappers;

import com.erp.erp.dto.EquipmentDTO;
import com.erp.erp.entity.*;
import com.erp.erp.repository.DepartmentRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.repository.InterventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentMapper {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private InterventionRepository interventionRepository;

    // Convert Equipment entity to EquipmentDTO
    public EquipmentDTO toDTO(Equipment equipment) {
        if (equipment == null) {
            return null;
        }

        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(equipment.getId());
        dto.setName(equipment.getName());
        dto.setDescription(equipment.getDescription());
        dto.setDateMiseEnService(equipment.getDateMiseEnService());
        if (equipment.getDepartment() != null) {
            dto.setDepartmentId(equipment.getDepartment().getId());
        }
        if (equipment.getPieces() != null) {
            List<String> pieceIds = equipment.getPieces().stream()
                    .map(Piece::getId)
                    .collect(Collectors.toList());
            dto.setPieces(pieceIds);
        }
        if (equipment.getInterventions() != null) {
            List<Integer> interventionIds = equipment.getInterventions().stream()
                    .map(Intervention::getId)
                    .collect(Collectors.toList());
            dto.setInterventions(interventionIds);
        }

        if (equipment.getParameterType() != null) {
            List<Long> parameterTypeIds = equipment.getParameterType().stream()
                    .map(ParameterType::getId)
                    .collect(Collectors.toList());
            dto.setParameterTypes(parameterTypeIds );
        }

        return dto;
    }

    // Convert EquipmentDTO to Equipment entity
    public Equipment toEntity(EquipmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Equipment equipment = new Equipment();
        equipment.setId(dto.getId());
        equipment.setName(dto.getName());
        equipment.setDescription(dto.getDescription());
        equipment.setDateMiseEnService(dto.getDateMiseEnService());

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + dto.getDepartmentId()));
            equipment.setDepartment(department);
        }

        // Attention: You should handle linking pieces and interventions carefully
        // during the save operation, not necessarily during simple mapping.

        return equipment;
    }
}
