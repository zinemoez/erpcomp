package com.erp.erp.controllers;


import com.erp.erp.dto.DailyParameterDTO;
import com.erp.erp.entity.DailyParameter;

import com.erp.erp.sevices.serv.IDailyParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/daily-parameters")
@RequiredArgsConstructor
public class DailyParameterController {

    private final IDailyParameterService dailyParameterService;


    //  Récupérer tous les paramètres
    @GetMapping
    public List<DailyParameterDTO> getAll() {
        return dailyParameterService.getAll();
    }

    //  Récupérer par ID
    @GetMapping("/{id}")
    public DailyParameterDTO getById(@PathVariable Long id) {
        return dailyParameterService.getById(id);
    }

    //  Récupérer par type de paramètre
    @GetMapping("/parameter-type/{parameterTypeId}")
    public List<DailyParameterDTO> getByParameterTypeId(@PathVariable Long parameterTypeId) {
        return dailyParameterService.findByParameterTypeId(parameterTypeId);
    }

    //  Récupérer par ID d'équipement
    @GetMapping("/equipment/{equipmentId}")
    public List<DailyParameterDTO> getByEquipmentId(@PathVariable String equipmentId) {
        return dailyParameterService.getByEquipmentId(equipmentId);
    }

    //  Récupérer par date
    @GetMapping("/date")
    public List<DailyParameterDTO> getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return dailyParameterService.getByDate(date);
    }

    //  Récupérer par équipement et date
    @GetMapping("/equipment/{equipmentId}/date")
    public List<DailyParameterDTO> getByEquipmentIdAndDate(
            @PathVariable String equipmentId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return dailyParameterService.getByEquipmentIdAndDate(equipmentId, date);
    }

    //  Créer ou mettre à jour un paramètre
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DailyParameterDTO createDailyParameter(@RequestBody DailyParameterDTO dailyParameterDTO) {
        return dailyParameterService.save(dailyParameterDTO);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DailyParameter updateDailyParameter(@PathVariable Long id,@RequestBody DailyParameterDTO dailyParameterDTO) {
        return dailyParameterService.updateDailyParameter(id,dailyParameterDTO);
    }
    @GetMapping("/department/{id}")
    public List<DailyParameterDTO> getByDepartementId(@PathVariable String id) {
        return dailyParameterService.findByDepartementId(id);
    }
    //  Supprimer un paramètre
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Long id) {
        dailyParameterService.delete(id);
    }


}
