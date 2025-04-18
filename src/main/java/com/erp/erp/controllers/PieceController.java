package com.erp.erp.controllers;

import com.erp.erp.dto.PieceDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.sevices.Imp.PieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/piece")
@RequiredArgsConstructor
public class PieceController {
    private final PieceService pieceService;

    @GetMapping("/all")
    public ResponseEntity<List<PieceDTO>> getAllPieces(){
        return ResponseEntity.ok(pieceService.getAllPieces());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PieceDTO> addPiece(@RequestBody @Valid PieceDTO pieceDTO){
        return ResponseEntity.ok(pieceService.addPiece(pieceDTO));
    }



    @GetMapping("/{id}")
    public ResponseEntity<PieceDTO> getPieceById(@PathVariable String id){
        return ResponseEntity.ok(pieceService.getPieceById(id));
    }

    @GetMapping("/equipment/{id}")
    public ResponseEntity<List<PieceDTO>> getPieceByEquipmentId(@PathVariable String id){
        return ResponseEntity.ok(pieceService.getPieceByEquipmentId(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PieceDTO>> getPieceByCategoryId(@PathVariable String id){
        return ResponseEntity.ok(pieceService.getPieceCategoryId(id));
    }



    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PieceDTO> updatePiece(@PathVariable String id, @RequestBody PieceDTO pieceDTO){
        return ResponseEntity.ok(pieceService.updatePiece(id,pieceDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deletePiece(@PathVariable String id){
        return ResponseEntity.ok(pieceService.deletePiece(id));
    }

}
