package com.erp.erp.repository;

import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.ParameterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParameterTypeRepository extends JpaRepository<ParameterType, Long> {
    @Query("SELECT par from ParameterType par where par.equipment.id=:id")
    List<ParameterType> findByEquipmentId(@Param("id") String id); // ex : pour lister les paramètres d’un équipement
    @Query("SELECT par from ParameterType par where par.equipment.department.id=:id")
    List<ParameterType> findByDepartmentId(@Param("id")  String id);
}