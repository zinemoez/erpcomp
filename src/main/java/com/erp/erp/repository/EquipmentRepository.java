package com.erp.erp.repository;

import com.erp.erp.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment,String> {

    @Query("SELECT equi from Equipment  equi where equi.department.id=:id")
    List<Equipment> findEquipmentByServiceId(@Param("id") String serviceId);
    Optional<Equipment> findById(String id);

}
