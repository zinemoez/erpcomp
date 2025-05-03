package com.erp.erp.sevices.serv;

import com.erp.erp.dto.EquipmentDTO;
import com.erp.erp.dto.Response;

import java.util.List;

public interface EquipmentService {
    EquipmentDTO addEquipment(EquipmentDTO equipmentDTO);
    EquipmentDTO updateEquipment(String id,EquipmentDTO equipmentDTO);
    List<EquipmentDTO> getAllEquipments();
    EquipmentDTO getEquipmentById(String id);
    List<EquipmentDTO> getEquipmentByDepartmentId(String id);
    Response deleteEquipment(String id);

}
