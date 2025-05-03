package com.erp.erp.sevices.serv;

import com.erp.erp.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO addDepartment(DepartmentDTO departmentDTODTO);
    DepartmentDTO  updateDepartment(String id,DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(String id);
    void deleteDepartment(String id);
}
