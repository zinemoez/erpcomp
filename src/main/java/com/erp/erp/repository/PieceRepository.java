package com.erp.erp.repository;

import com.erp.erp.entity.Category;
import com.erp.erp.entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PieceRepository extends JpaRepository<Piece,String> {
    @Query("SELECT  p from Piece p where p.equipment.id=:id")
    List<Piece> findPiecesByEquipmentId(@Param("id") String id);
    @Query("SELECT  p from Piece p where p.category.id=:id")
    List<Piece> findPiecesByCategoryId(@Param("id") String id);
}
