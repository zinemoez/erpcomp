package com.erp.erp.sevices.serv;

import com.erp.erp.dto.DailyParameterDTO;
import com.erp.erp.entity.DailyParameter;

import java.util.Date;
import java.util.List;

public interface IDailyParameterService {

    // 🔹 Récupérer tous les paramètres journaliers
    List<DailyParameterDTO> getAll();

    // 🔹 Par ID de type de paramètre
    List<DailyParameterDTO> getByParameterTypeId(Long parameterTypeId);

    // 🔹 Par ID d'équipement
    List<DailyParameterDTO> getByEquipmentId(String equipmentId);

    // 🔹 Par date
    List<DailyParameterDTO> getByDate(Date date);

    // 🔹 Par équipement + date
    List<DailyParameterDTO> getByEquipmentIdAndDate(String equipmentId, Date date);

    // 🔹 Ajouter ou mettre à jour un paramètre
    DailyParameterDTO  save(DailyParameterDTO dailyParameterDTO);

    // 🔹 Supprimer un paramètre par ID
    void delete(Long id);

    // 🔹 Récupérer un paramètre par ID
    DailyParameterDTO getById(Long id);
    List<DailyParameterDTO> findByDepartementId( String id);
    DailyParameter updateDailyParameter(Long id,DailyParameterDTO dailyParameterDTO);

    List<DailyParameterDTO> findByParameterTypeId(Long id);
}

