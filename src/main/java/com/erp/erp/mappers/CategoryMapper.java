package com.erp.erp.mappers;

import com.erp.erp.dto.CategoryDTO;
import com.erp.erp.entity.Category;
import com.erp.erp.entity.Piece;
import com.erp.erp.repository.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryMapper {

    @Autowired
    private PieceRepository pieceRepository;

    // Convert Category entity to CategoryDTO
    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        // Convert pieces to a list of IDs
        List<String> pieceIds = category.getPieces().stream()
                .map(Piece::getId) // Assuming Piece's ID is a String, if not modify accordingly
                .collect(Collectors.toList());

        dto.setPieces(pieceIds);

        return dto;
    }

    // Convert CategoryDTO to Category entity
    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        // Convert piece IDs to Piece entities
        if (dto.getPieces() != null && !dto.getPieces().isEmpty()) {
            List<Piece> pieces = pieceRepository.findAllById(dto.getPieces());
            category.setPieces(pieces);
        }

        return category;
    }
}
