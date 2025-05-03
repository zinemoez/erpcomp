package com.erp.erp.sevices.impl;

import com.erp.erp.dto.*;
import com.erp.erp.entity.Department;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.User;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.mappers.DepartmentMapper;
import com.erp.erp.mappers.EquipmentMapper;
import com.erp.erp.mappers.UserMapper;
import com.erp.erp.repository.DepartmentRepository;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.UserRepository;
import com.erp.erp.sevices.serv.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    private  final UserMapper userMapper;
    private final EquipmentMapper equipmentMapper;// Use custom DepartmentMapper
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        // Map DTO to Entity using DepartmentMapper
        Department department = departmentMapper.toEntity(departmentDTO);

        // Handle Users if provided
        if (departmentDTO.getUsers() != null) {
            List<User> users = departmentDTO.getUsers().stream()
                    .map(userDTO -> userRepository.findById(userDTO)
                            .orElseThrow(() -> new NotFoundException("User Not Found")))
                    .collect(Collectors.toList());
            department.setUsers(users);
        }

        // Handle Equipments if provided
        if (departmentDTO.getEquipmentsId() != null) {
            List<Equipment> equipments = departmentDTO.getEquipmentsId().stream()
                    .map(equipmentDTO -> equipmentRepository.findById(equipmentDTO)
                            .orElseThrow(() -> new NotFoundException("Equipment Not Found")))
                    .collect(Collectors.toList());
            department.setEquipments(equipments);
        }

        // Save the new department
        Department savedDepartment = departmentRepository.save(department);

        // Convert to DTO for response using DepartmentMapper
        return departmentMapper.toDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
        // Fetch the existing department or throw an exception if not found
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department Not Found"));

        // Update basic fields
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());

        // Update Users if provided
        if (departmentDTO.getUsers() != null) {
            List<User> users = departmentDTO.getUsers().stream()
                    .map(userDTO -> userRepository.findById(userDTO)
                            .orElseThrow(() -> new NotFoundException("User Not Found")))
                    .collect(Collectors.toList());
            department.setUsers(users);
        }

        // Update Equipments if provided
        if (departmentDTO.getEquipmentsId() != null) {
            List<Equipment> equipments = departmentDTO.getEquipmentsId().stream()
                    .map(equipmentDTO -> equipmentRepository.findById(equipmentDTO)
                            .orElseThrow(() -> new NotFoundException("Equipment Not Found")))
                    .collect(Collectors.toList());
            department.setEquipments(equipments);
        }

        // Save the updated department
        Department updatedDepartment = departmentRepository.save(department);

        // Convert updated department back to DTO using DepartmentMapper
        return departmentMapper.toDTO(updatedDepartment);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return departments.stream().map(department -> {
            // Initialize the collections to avoid lazy loading issues
            department.getUsers().size();
            department.getEquipments().size();
            // Convert department to DTO using DepartmentMapper
            DepartmentDTO departmentDTO = departmentMapper.toDTO(department);
            // Map users if needed
            if (department.getUsers() != null) {
                // Map user IDs if users exist, assuming that userMapper returns UserDTO and has getId() method
                List<Long> userDTOs = department.getUsers().stream()
                        .map(user -> userMapper.toUserDTO(user).getId())  // Convert to UserDTO and get the ID
                        .collect(Collectors.toList());
                departmentDTO.setUsers(userDTOs);  // Set the list of user IDs
            }
            // Map equipments if needed
            if (department.getEquipments() != null) {
                // Map equipment IDs if equipments exist, assuming that equipmentMapper returns EquipmentDTO and has getId() method
                List<String> equipmentDTOs = department.getEquipments().stream()
                        .map(equipment -> equipmentMapper.toDTO(equipment).getId())  // Convert to EquipmentDTO and get the ID
                        .collect(Collectors.toList());
                departmentDTO.setEquipmentsId(equipmentDTOs);  // Set the list of equipment IDs
            }
            return departmentDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(String id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department Not Found"));

        // Convert department to DTO using DepartmentMapper
        DepartmentDTO departmentDTO = departmentMapper.toDTO(department);

        // Map users and equipments if needed
        if (department.getUsers() != null) {
            List<Long> userDTOs = department.getUsers().stream()
                    .map(user -> userMapper.toUserDTO(user).getId())  // Use DepartmentMapper to convert users
                    .collect(Collectors.toList());
            departmentDTO.setUsers(userDTOs);
        }

        if (department.getEquipments() != null) {
            List<String> equipmentDTOs = department.getEquipments().stream()
                    .map(equipment -> equipmentMapper.toDTO(equipment).getId())  // Use DepartmentMapper to convert equipments
                    .toList();
            departmentDTO.setEquipmentsId(equipmentDTOs);
        }

        return departmentDTO;
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department Not Found"));

        departmentRepository.deleteById(id);

        log.info("Department with ID {} has been deleted", id);
    }
}
