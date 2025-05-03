package com.erp.erp.sevices.impl;

import com.erp.erp.entity.LoginRequest;
import com.erp.erp.entity.RegisterRequest;
import com.erp.erp.dto.Response;
import com.erp.erp.dto.UserDTO;
import com.erp.erp.entity.User;
import com.erp.erp.exceptions.InvalidCredentialsException;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.DepartmentRepository;
import com.erp.erp.repository.UserRepository;
import com.erp.erp.security.JwtUtils;
import com.erp.erp.sevices.serv.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final DepartmentRepository departmentRepository;

    @Override
    public RegisterRequest registerUser(RegisterRequest registerRequest) {



        User userToSave = modelMapper.map(registerRequest, User.class);
        userToSave.setDepartment(departmentRepository.findById(registerRequest.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department not found with ID: " + registerRequest.getDepartmentId())));
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        userRepository.save(userToSave);
        return registerRequest;
    }

    @Override
    public RegisterRequest registerUserLogin(RegisterRequest registerRequest) {
        userRepository.save(modelMapper.map(registerRequest, User.class));
        return registerRequest;
    }

    @Override
    public  UserDTO findUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
             UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return userDTO;

    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("Email not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("password does not match");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("User logged in successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 months")
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return users.stream().map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            if (user.getDepartment() != null) {
                userDTO.setDepartmentId(user.getDepartment().getId().toString());
            }
            return userDTO;
        }).toList();
    }

    @Override
    public UserDTO getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getNaissance() != null) existingUser.setNaissance(userDTO.getNaissance());
        if (userDTO.getName() != null) existingUser.setName(userDTO.getName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getRole() != null) existingUser.setRole(userDTO.getRole());
        if (userDTO.getAdresse() != null) existingUser.setAdresse(userDTO.getAdresse());
        if (userDTO.getCin() != null) existingUser.setCin(userDTO.getCin());
        if (userDTO.getPoste() != null) existingUser.setPoste(userDTO.getPoste());
        if (userDTO.getCategorie() != null) existingUser.setCategorie(userDTO.getCategorie());

        if (userDTO.getDepartmentId() != null) {
            existingUser.setDepartment(departmentRepository.findById(userDTO.getDepartmentId())
                    .orElseThrow(() -> new NotFoundException("Department not found with ID: " + userDTO.getDepartmentId())));
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User successfully deleted")
                .build();
    }

    @Override
    public Response getUserTransactions(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.builder()
                .status(200)
                .message("Success")
                .user(userDTO.getId())
                .build();
    }

    @Override
    public List<UserDTO> findUserByDepartmentId(String id) {
        List<User> users = userRepository.findUserByDepartmentId(id);
        return users.stream().map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            if (user.getDepartment() != null) {
                userDTO.setDepartmentId(user.getDepartment().getId().toString());
            }
            return userDTO;
        }).toList();
    }
}

