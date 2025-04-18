package com.erp.erp.sevices.Imp;


import com.erp.erp.entity.LoginRequest;
import com.erp.erp.entity.RegisterRequest;
import com.erp.erp.dto.Response;
import com.erp.erp.dto.UserDTO;


import java.util.List;

public interface UserService {
    RegisterRequest registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    List<UserDTO> getAllUsers();
    UserDTO getCurrentLoggedInUser();
    UserDTO updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransactions(Long id);
    List<UserDTO>  findUserByDepartmentId(String id);
    RegisterRequest registerUserLogin(RegisterRequest registerRequest);
    UserDTO findUserById(Long id);
}
