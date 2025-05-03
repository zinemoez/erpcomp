package com.erp.erp.sevices.impl;

import com.erp.erp.dto.PieceDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.Category;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.Piece;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.CategoryRepository;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.sevices.serv.PieceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import com.erp.erp.mappers.PieceMapper;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PieceServiceImpl implements PieceService {
    private final PieceRepository pieceRepository;
    private final PieceMapper pieceMapper;
    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PieceDTO addPiece(PieceDTO pieceDTO) {
        Piece piece = pieceMapper.toPiece(pieceDTO);
        pieceRepository.save(piece);
        return pieceMapper.toPieceDTO(piece);
    }

    @Override
    public PieceDTO updatePiece(String id, PieceDTO pieceDTO) {
        Piece existingPiece = pieceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Piece not found with id: " + id));

        existingPiece.setName(pieceDTO.getName());
        existingPiece.setSku(pieceDTO.getSku());
        existingPiece.setPrice(pieceDTO.getPrice());
        existingPiece.setDescription(pieceDTO.getDescription());
        existingPiece.setExpiryDate(pieceDTO.getExpiryDate());
        existingPiece.setUpdatedAt(new Date());

        if (pieceDTO.getStockQuantity() != null) {
            existingPiece.setStockQuantity(pieceDTO.getStockQuantity());
        }

        if (pieceDTO.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(pieceDTO.getEquipmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + pieceDTO.getEquipmentId()));
            existingPiece.setEquipment(equipment);
        }

        if (pieceDTO.getCategory() != null) {
            Category category = categoryRepository.findById(pieceDTO.getCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + pieceDTO.getCategory()));
            existingPiece.setCategory(category);
        }

        pieceRepository.save(existingPiece);
        return pieceMapper.toPieceDTO(existingPiece);
    }

    @Override
    public List<PieceDTO> getAllPieces() {
        List<Piece> pieces = pieceRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return pieces.stream().map(pieceMapper::toPieceDTO).collect(Collectors.toList());
    }

    @Override
    public PieceDTO getPieceById(String id) {
        Piece piece = pieceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Piece Not Found"));
        return pieceMapper.toPieceDTO(piece);
    }

    @Override
    public List<PieceDTO> getPieceByEquipmentId(String id) {
        List<Piece> pieces = pieceRepository.findPiecesByEquipmentId(id);
        return pieces.stream().map(pieceMapper::toPieceDTO).collect(Collectors.toList());
    }

    @Override
    public List<PieceDTO> getPieceCategoryId(String id) {
        List<Piece> pieces = pieceRepository.findPiecesByCategoryId(id);
        return pieces.stream().map(pieceMapper::toPieceDTO).collect(Collectors.toList());
    }

    @Override
    public Response deletePiece(String id) {
        pieceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Piece Not Found"));
        pieceRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Piece successfully deleted")
                .build();
    }
}
