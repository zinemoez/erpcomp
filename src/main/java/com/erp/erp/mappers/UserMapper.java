package com.erp.erp.mappers;

import com.erp.erp.dto.UserDTO;
import com.erp.erp.entity.Department;
import com.erp.erp.entity.User;
import com.erp.erp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final DepartmentRepository departmentRepository;

    // Convert User Entity to UserDTO
    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setNaissance(user.getNaissance());
        userDTO.setCin(user.getCin());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPoste(user.getPoste());
        userDTO.setCategorie(user.getCategorie());
        userDTO.setAdresse(user.getAdresse());
        userDTO.setRole(user.getRole());

        if (user.getDepartment() != null) {
            userDTO.setDepartmentId(String.valueOf(user.getDepartment().getId()));
        }

        return userDTO;
    }

    // Convert UserDTO to User Entity
    public User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setNaissance(userDTO.getNaissance());
        user.setCin(userDTO.getCin());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPoste(userDTO.getPoste());
        user.setCategorie(userDTO.getCategorie());
        user.setAdresse(userDTO.getAdresse());
        user.setRole(userDTO.getRole());

        if (userDTO.getDepartmentId() != null) {
            try {
                Department department = departmentRepository.findById(userDTO.getDepartmentId())
                        .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + userDTO.getDepartmentId()));
                user.setDepartment(department);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid department ID format: " + userDTO.getDepartmentId());
            }
        }

        return user;
    }
}
