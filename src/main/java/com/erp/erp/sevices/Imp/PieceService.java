package com.erp.erp.sevices.Imp;

import com.erp.erp.dto.PieceDTO;
import com.erp.erp.dto.Response;

import java.util.List;

public interface PieceService {
    PieceDTO addPiece(PieceDTO pieceDTO);
    PieceDTO updatePiece(String id,PieceDTO pieceDTO);
    List<PieceDTO> getAllPieces();
    PieceDTO getPieceById(String id);
    List<PieceDTO>  getPieceByEquipmentId(String id);
    List<PieceDTO>  getPieceCategoryId(String id);
    Response deletePiece(String id);
}
