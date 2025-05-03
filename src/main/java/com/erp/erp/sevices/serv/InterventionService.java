package com.erp.erp.sevices.serv;

import com.erp.erp.dto.InterventionDTO;
import com.erp.erp.dto.Response;

import java.util.List;

public interface InterventionService {
    InterventionDTO addIntervention(InterventionDTO interventionDTO);
    InterventionDTO updateIntervention(int id,InterventionDTO interventionDTO);
    List<InterventionDTO> getAllInterventions();
    InterventionDTO getInterventionById(int id);
    List<InterventionDTO>  getInterventionByEquipmentId(String id);
    Response deleteIntervention(int id);
    Response ApproveIntervention(int id );

    List<InterventionDTO> getInterventionCreatedBy(Long id);

    List<InterventionDTO> getInterventionApprouvedBy(Long id);

    List<InterventionDTO> getInterventionByPieceId(String id);
}
