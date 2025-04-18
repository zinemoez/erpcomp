package com.erp.erp.controllers;

import com.erp.erp.dto.EquipmentDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.sevices.Imp.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/equi")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping("/all")
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment(){
        return ResponseEntity.ok(equipmentService.getAllEquipments());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EquipmentDTO> addDepartment( @RequestBody @Valid EquipmentDTO equipmentDTO){
        return ResponseEntity.ok(equipmentService.addEquipment(equipmentDTO));
    }



    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(@PathVariable String id){
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @GetMapping("/dep/{id}")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentByDepartmentId(@PathVariable String id){
        return ResponseEntity.ok(equipmentService.getEquipmentByDepartmentId(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable String id, @RequestBody EquipmentDTO equipmentDTO){
        return ResponseEntity.ok(equipmentService.updateEquipment(id,equipmentDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteDepartment(@PathVariable String id){
        return ResponseEntity.ok(equipmentService.deleteEquipment(id));
    }
}
