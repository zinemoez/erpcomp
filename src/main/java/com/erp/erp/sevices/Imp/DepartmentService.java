package com.erp.erp.sevices.Imp;

import com.erp.erp.dto.EquipmentDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.dto.DepartmentDTO;
import com.erp.erp.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO addDepartment(DepartmentDTO departmentDTODTO);
    DepartmentDTO  updateDepartment(String id,DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAlldepartments();
    DepartmentDTO getDepartmentById(String id);
    void deleteDepartment(String id);
}
