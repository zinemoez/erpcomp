package com.erp.erp.sevices.impl;

import com.erp.erp.dto.PieceDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.Piece;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.CategoryRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.sevices.Imp.PieceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PieceServiceImpl implements PieceService {
    private final PieceRepository pieceRepository;
    private final ModelMapper modelMapper;
    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PieceDTO addPiece(PieceDTO pieceDTO) {
        Piece piece = modelMapper.map(pieceDTO, Piece.class);
        pieceRepository.save(piece);
        return pieceDTO;
    }

    @Override
    public PieceDTO updatePiece(String id,PieceDTO pieceDTO) {
       Piece piece=pieceRepository.findById(id).get();
       piece.setDescription(pieceDTO.getDescription());
       piece.setName(pieceDTO.getName());
       piece.setPrice(pieceDTO.getPrice());
       piece.setStockQuantity(pieceDTO.getStockQuantity());
       piece.setSku(pieceDTO.getSku());
        piece.setExpiryDate(pieceDTO.getExpiryDate());
        piece.setUpdatedAt(new Date());
        piece.setCategory(pieceDTO.getCategory());
        piece.setEquipment(pieceDTO.getEquipmentId());
       pieceRepository.save(piece);
        return pieceDTO;
    }

    @Override
    public List<PieceDTO> getAllPieces() {
        List<Piece> pieces = pieceRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));;
        List<PieceDTO> pieceDTOS = modelMapper.map(pieces, new TypeToken<List<PieceDTO>>() {}.getType());
        return pieceDTOS;
    }

    @Override
    public PieceDTO getPieceById(String id) {
        Piece piece = pieceRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Piece Not Found"));
    PieceDTO pieceDTO = modelMapper.map(piece, PieceDTO.class);
    pieceDTO.setId(piece.getId());
        return pieceDTO;
    }

    @Override
    public List<PieceDTO> getPieceByEquipmentId(String id) {
    List<Piece> pieces=pieceRepository.findPiecesByEquipmentId(id);
    List<PieceDTO> piecesDTOS=modelMapper.map(pieces, new TypeToken<List<PieceDTO>>() {}.getType());
         return piecesDTOS;
    }

    @Override
    public List<PieceDTO> getPieceCategoryId(String id) {
        List<Piece> pieces=pieceRepository.findPiecesByCategoryId(id);
        List<PieceDTO> piecesDTOS=modelMapper.map(pieces, new TypeToken<List<PieceDTO>>() {}.getType());
        return piecesDTOS;
    }

    @Override
    public Response deletePiece(String id) {
        pieceRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Piece Not Found"));
        pieceRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Piece successfully deleted")
                .build();
    }
}
