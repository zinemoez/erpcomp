package com.erp.erp.mappers;

import com.erp.erp.dto.PieceDTO;
import com.erp.erp.entity.Category;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.Piece;
import com.erp.erp.repository.CategoryRepository;
import com.erp.erp.repository.EquipmentRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PieceMapper {

    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;

    // Convert Piece entity to PieceDTO
    public PieceDTO toPieceDTO(Piece piece) {
        if (piece == null) {
            return null;
        }

        PieceDTO dto = new PieceDTO();
        dto.setId(piece.getId());
        dto.setName(piece.getName());
        dto.setSku(piece.getSku());
        dto.setPrice(piece.getPrice());
        dto.setDescription(piece.getDescription());
        dto.setExpiryDate(piece.getExpiryDate());
        dto.setUpdatedAt(piece.getUpdatedAt());
        dto.setCreatedAt(piece.getCreatedAt());

        if (piece.getStockQuantity() != null) {
            dto.setStockQuantity(piece.getStockQuantity().longValue());
        }

        if (piece.getEquipment() != null) {
            dto.setEquipmentId(piece.getEquipment().getId());
        }

        if (piece.getCategory() != null) {
            dto.setCategory(piece.getCategory().getId());
        }

        return dto;
    }

    // Convert PieceDTO to Piece entity
    public Piece toPiece(PieceDTO dto) {
        if (dto == null) {
            return null;
        }

        Piece piece = new Piece();
        piece.setId(dto.getId());
        piece.setName(dto.getName());
        piece.setSku(dto.getSku());
        piece.setPrice(dto.getPrice());
        piece.setDescription(dto.getDescription());
        piece.setExpiryDate(dto.getExpiryDate());
        piece.setUpdatedAt(dto.getUpdatedAt());
        // createdAt is final, cannot set manually

        if (dto.getStockQuantity() != null) {
            piece.setStockQuantity(dto.getStockQuantity());
        }

        if (dto.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + dto.getEquipmentId()));
            piece.setEquipment(equipment);
        }

        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(dto.getCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + dto.getCategory()));
            piece.setCategory(category);
        }

        return piece;
    }
}
