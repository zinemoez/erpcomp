package com.erp.erp.sevices.impl;

import com.erp.erp.dto.*;
import com.erp.erp.entity.Department;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.User;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.DepartmentRepository;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.UserRepository;
import com.erp.erp.sevices.Imp.DepartmentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
        private final ModelMapper modelMapper;
        private final DepartmentRepository departmentRepository ;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {

        // Map DTO to Entity
        Department department = modelMapper.map(departmentDTO, Department.class);

        // Handle Users if provided
        if (departmentDTO.getUsers() != null) {
            List<User> users = departmentDTO.getUsers().stream()
                    .map(userDTO -> userRepository.findById(userDTO.getId())
                            .orElseThrow(() -> new NotFoundException("User Not Found")))
                    .collect(Collectors.toList());
            department.setUsers(users);
        }

        // Handle Equipments if provided
        if (departmentDTO.getEquipmentsId() != null) {
            List<Equipment> equipments = departmentDTO.getEquipmentsId().stream()
                    .map(equipmentDTO -> equipmentRepository.findById(equipmentDTO.getId())
                            .orElseThrow(() -> new NotFoundException("Equipment Not Found")))
                    .collect(Collectors.toList());
            department.setEquipments(equipments);
        }

        // Save the new department
        departmentRepository.save(department);

        // Convert to DTO for response
        DepartmentDTO savedDepartmentDTO = modelMapper.map(department, DepartmentDTO.class);

        return savedDepartmentDTO;

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
                    .map(userDTO -> userRepository.findById(userDTO.getId())
                            .orElseThrow(() -> new NotFoundException("User Not Found")))
                    .collect(Collectors.toList());
            department.setUsers(users);
        }

        // Update Equipments if provided
        if (departmentDTO.getEquipmentsId() != null) {
            List<Equipment> equipments = departmentDTO.getEquipmentsId().stream()
                    .map(equipmentDTO -> equipmentRepository.findById(equipmentDTO.getId())
                            .orElseThrow(() -> new NotFoundException("Equipment Not Found")))
                    .collect(Collectors.toList());
            department.setEquipments(equipments);
        }

        // Save the updated department
        departmentRepository.save(department);

        // Convert updated department back to DTO
        DepartmentDTO updatedDepartmentDTO = modelMapper.map(department, DepartmentDTO.class);

        return updatedDepartmentDTO;

    }
    
    @Override
    public List<DepartmentDTO> getAlldepartments() {

        List<Department> departments = departmentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return departments.stream().map(department -> {
                    department.getUsers().size();
                    department.getEquipments().size();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);

                    List<UserDTO> userDTOs = department.getUsers().stream()
                            .map(user -> modelMapper.map(user, UserDTO.class))
                            .collect(Collectors.toList());
                    departmentDTO.setUsers(userDTOs);

                    List<EquipmentDTO> equipmentDTOs = department.getEquipments().stream()
                            .map(equipment -> modelMapper.map(equipment, EquipmentDTO.class))
                            .collect(Collectors.toList());
                    departmentDTO.setEquipmentsId(equipmentDTOs);

            return departmentDTO;
        }
        ).collect(Collectors.toList());

    }

    @Override
    public DepartmentDTO getDepartmentById(String id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department Not Found"));

        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);

        // Mapper les utilisateurs
        if (department.getUsers() != null) {
            departmentDTO.setUsers(department.getUsers().stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList()));
        }

        // Mapper les équipements
        if (department.getEquipments() != null) {
            departmentDTO.setEquipmentsId(department.getEquipments().stream()
                    .map(equipment -> modelMapper.map(equipment, EquipmentDTO.class))
                    .collect(Collectors.toList()));
        }

        return departmentDTO;
    }

        @Override
        public void deleteDepartment(String id) {
            departmentRepository.findById(id)
                    .orElseThrow(()-> new NotFoundException("Department Not Found"));

            departmentRepository.deleteById(id);

        }
    }

