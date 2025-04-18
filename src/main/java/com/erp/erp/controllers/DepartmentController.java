package com.erp.erp.controllers;

import com.erp.erp.dto.DepartmentDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.Department;
import com.erp.erp.sevices.Imp.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dep")
@RequiredArgsConstructor
@Tag(name = "DEPARTEMENT")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(){
        return ResponseEntity.ok(departmentService.getAlldepartments());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentDTO > addDepartment( @RequestBody @Valid DepartmentDTO departmentDTO){
        return ResponseEntity.ok(departmentService.addDepartment(departmentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable String id){
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentDTO > updateDepartment(@PathVariable String id, @RequestBody DepartmentDTO departmentDTO){
    return ResponseEntity.ok(departmentService.updateDepartment(id,departmentDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(@PathVariable String id){
       departmentService.deleteDepartment(id);
    }


}
