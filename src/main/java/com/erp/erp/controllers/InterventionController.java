package com.erp.erp.controllers;

import com.erp.erp.dto.InterventionDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.sevices.serv.InterventionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inter")
@RequiredArgsConstructor
public class InterventionController {
    private final InterventionService interventionService;

    @GetMapping("/all")
    public ResponseEntity<List<InterventionDTO>> getAllIntervention(){
        return ResponseEntity.ok(interventionService.getAllInterventions());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<InterventionDTO> addIntervention(@RequestBody @Valid InterventionDTO interventionDTO){
        return ResponseEntity.ok(interventionService.addIntervention(interventionDTO));
    }



    @GetMapping("/{id}")
    public ResponseEntity<InterventionDTO> getInterventionById(@PathVariable int id){
        return ResponseEntity.ok(interventionService.getInterventionById(id));
    }

    @GetMapping("/equi/{id}")
    public ResponseEntity<List<InterventionDTO>> getInterventionByEquipmentId(@PathVariable String id){
        return ResponseEntity.ok(interventionService.getInterventionByEquipmentId(id));
    }

    @GetMapping("/createdBy/{id}")
    public ResponseEntity<List<InterventionDTO>> getInterventionByCreatedId(@PathVariable Long  id) {
        return ResponseEntity.ok(interventionService.getInterventionCreatedBy(id));
    }

    @GetMapping("/approuvedBy/{id}")
    public ResponseEntity<List<InterventionDTO>> getInterventionByApprouvedId(@PathVariable long id) {
        return ResponseEntity.ok(interventionService.getInterventionApprouvedBy(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<InterventionDTO> updateIntervention(@PathVariable int id, @RequestBody InterventionDTO interventionDTO){
        return ResponseEntity.ok(interventionService.updateIntervention( id,interventionDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteDepartment(@PathVariable int id){
        return ResponseEntity.ok(interventionService.deleteIntervention(id));
    }
    @GetMapping("/piece/{id}")
    public ResponseEntity<List<InterventionDTO>> getInterventionByPieceId(@PathVariable String id){
        return ResponseEntity.ok(interventionService.getInterventionByPieceId(id));
    }

}
