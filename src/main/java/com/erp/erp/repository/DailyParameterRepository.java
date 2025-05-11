package com.erp.erp.repository;

import com.erp.erp.entity.DailyParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface DailyParameterRepository extends JpaRepository<DailyParameter, Long> {
    @Query("SELECT p FROM DailyParameter p JOIN FETCH p.parameterType WHERE p.parameterType.id = :id")
    List<DailyParameter> findByParameterTypeId(@Param("id") Long id);
    @Query("SELECT p FROM DailyParameter p WHERE p.parameterType.equipment.id = :equipmentId")
    List<DailyParameter> findByEquipmentId(@Param("equipmentId") String equipmentId);
    List<DailyParameter> findByDate(Date date);
    @Query("SELECT p FROM DailyParameter p WHERE p.parameterType.equipment.id = :equipmentId AND p.date = :date")
    List<DailyParameter> findByEquipmentIdAndDate(@Param("equipmentId") String equipmentId, @Param("date") Date date);
    @Query("SELECT p FROM DailyParameter p WHERE p.parameterType.equipment.department.id = :id")
    List<DailyParameter> findByDepartementId(@Param("equipmentId") String id);

}
