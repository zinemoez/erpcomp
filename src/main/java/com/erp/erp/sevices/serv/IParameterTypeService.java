package com.erp.erp.sevices.serv;

import com.erp.erp.dto.ParameterTypeDTO;

import java.util.List;

public interface IParameterTypeService {

    ParameterTypeDTO save(ParameterTypeDTO parameterTypeDTO);

    List<ParameterTypeDTO> getByEquipmentId(String id);
    List<ParameterTypeDTO> getByDepartmentId(String id);

    List<ParameterTypeDTO> getAll();
    ParameterTypeDTO getById(Long id);

    void delete(Long id);

    ParameterTypeDTO updateParameterType(Long id, ParameterTypeDTO parameterTypeDTO);
}
