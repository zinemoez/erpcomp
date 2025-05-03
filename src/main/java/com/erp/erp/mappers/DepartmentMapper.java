package com.erp.erp.mappers;
import com.erp.erp.dto.DepartmentDTO;
import com.erp.erp.entity.Department;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.User;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentMapper {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    // Convert Department entity to DepartmentDTO
    public DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .createdAt(department.getCreatedAt())
                .users(department.getUsers() != null ?
                        department.getUsers().stream().map(User::getId).collect(Collectors.toList()) : null)
                .equipmentsId(department.getEquipments() != null ?
                        department.getEquipments().stream().map(Equipment::getId).collect(Collectors.toList()) : null)
                .build();
    }

    // Convert DepartmentDTO to Department entity
    public Department toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setCreatedAt(dto.getCreatedAt());

        if (dto.getUsers() != null) {
            List<User> users = userRepository.findAllById(dto.getUsers());
            department.setUsers(users);
        }

        if (dto.getEquipmentsId() != null) {
            List<Equipment> equipments = equipmentRepository.findAllById(dto.getEquipmentsId());
            department.setEquipments(equipments);
        }

        return department;
    }
}
