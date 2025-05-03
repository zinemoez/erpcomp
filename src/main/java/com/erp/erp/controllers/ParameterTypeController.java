package com.erp.erp.controllers;

import com.erp.erp.dto.ParameterTypeDTO;
import com.erp.erp.sevices.serv.IParameterTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parameterTypes")
@RequiredArgsConstructor
public class ParameterTypeController {

    private final IParameterTypeService parameterTypeService;



    // 🔹 GET all parameter types
    @GetMapping
    public ResponseEntity<List<ParameterTypeDTO>> getAll() {
        return ResponseEntity.ok(parameterTypeService.getAll());
    }

    // 🔹 GET parameter type by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParameterTypeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(parameterTypeService.getById(id));
    }

    // 🔹 GET parameter types by Equipment ID
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<ParameterTypeDTO>> getByEquipmentId(@PathVariable String equipmentId) {
        return ResponseEntity.ok(parameterTypeService.getByEquipmentId(equipmentId));
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<ParameterTypeDTO>> getByDepartmentId(@PathVariable String departmentId) {
        return ResponseEntity.ok(parameterTypeService.getByDepartmentId(departmentId));
    }
    // 🔹 POST: Create or Update parameter type
    @PostMapping("/add")
    public ResponseEntity<ParameterTypeDTO> addParameterType(@RequestBody ParameterTypeDTO dto) {
        ParameterTypeDTO saved = parameterTypeService.save(dto);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ParameterTypeDTO updateParameterType(@PathVariable Long id, @RequestBody ParameterTypeDTO parameterTypeDTO) {
        return parameterTypeService.updateParameterType(id,parameterTypeDTO);
    }
    // 🔹 DELETE parameter type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parameterTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

