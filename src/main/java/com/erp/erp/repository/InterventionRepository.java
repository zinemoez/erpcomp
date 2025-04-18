package com.erp.erp.repository;

import com.erp.erp.dto.InterventionDTO;
import com.erp.erp.entity.Intervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Integer> {
    List<Intervention> findByCreatedByIdOrderByCreatedAtDesc(Long createdById);
    List<Intervention> findByApprovedByIdOrderByCreatedAtDesc(Long approvedById);

    @Query("SELECT t FROM Intervention t " +
            "WHERE (:searchText IS NULL OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.status) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "CAST(t.id AS string) LIKE CONCAT('%', :searchText, '%'))")
    Page<Intervention> searchIntervention(@Param("searchText") String searchText, Pageable pageable);
    @Query("select i from Intervention i where i.equipment.id=:id")
    List<Intervention> findByEquipmentId(@Param("id") String id);

    @Query("SELECT i FROM Intervention i JOIN i.pieces p WHERE p.id = :id")
    List<Intervention> findByPieceId(@Param("id") String id);
}