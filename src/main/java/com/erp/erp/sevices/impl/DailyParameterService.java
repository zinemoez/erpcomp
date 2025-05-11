package com.erp.erp.sevices.impl;
import com.erp.erp.dto.DailyParameterDTO;
import com.erp.erp.entity.DailyParameter;
import com.erp.erp.entity.ParameterType;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.mappers.DailyParameterMapper;
import com.erp.erp.repository.DailyParameterRepository;
import com.erp.erp.repository.ParameterTypeRepository;
import com.erp.erp.sevices.serv.IDailyParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyParameterService implements IDailyParameterService {

    private final DailyParameterRepository dailyParameterRepository;
    private final ParameterTypeRepository parameterTypeRepository;
    private final DailyParameterMapper dailyParameterMapper;

    @Autowired
    public DailyParameterService(DailyParameterRepository dailyParameterRepository, ParameterTypeRepository parameterTypeRepository, DailyParameterMapper dailyParameterMapper) {
        this.dailyParameterRepository = dailyParameterRepository;
        this.parameterTypeRepository = parameterTypeRepository;
        this.dailyParameterMapper = dailyParameterMapper;
    }

    @Override
    public List<DailyParameterDTO> getAll() {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findAll();

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des paramètres : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<DailyParameterDTO> getByParameterTypeId(Long parameterTypeId) {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findByParameterTypeId(parameterTypeId);

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des paramètres : " + e.getMessage());
            return new ArrayList<>();
        }
    }



    @Override
    public List<DailyParameterDTO> getByEquipmentId(String equipmentId) {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findByEquipmentId(equipmentId);

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des paramètres pour l'équipement : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<DailyParameterDTO> getByDate(Date date) {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findByDate(date);

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des paramètres par date : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<DailyParameterDTO> getByEquipmentIdAndDate(String equipmentId, Date date) {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findByEquipmentIdAndDate(equipmentId, date);

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des paramètres pour l’équipement "
                    + equipmentId + " à la date " + date + " : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public DailyParameterDTO save(DailyParameterDTO dailyParameterDTO) {
        DailyParameter dailyParameter = dailyParameterMapper.toEntity(dailyParameterDTO);
        dailyParameterRepository.save(dailyParameter);
        return dailyParameterDTO ;
    }


    @Override
    public void delete(Long id) {
        dailyParameterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("equipment Not Found"));
        dailyParameterRepository.deleteById(id);
    }

    @Override
    public DailyParameterDTO getById(Long id) {
        try {
            DailyParameter dailyParameter = dailyParameterRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("DailyParameter not found with id: " + id));

            return dailyParameterMapper.toDTO(dailyParameter);

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du DailyParameter avec id " + id + " : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<DailyParameterDTO> findByDepartementId(String id) {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findByDepartementId(id);

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des DailyParameters pour le département " + id + " : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public DailyParameter updateDailyParameter(Long id, DailyParameterDTO dailyParameterDTO) {
        // Retrieve the existing DailyParameter
        DailyParameter existing = dailyParameterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DailyParameter not found with ID: " + id));

        // Map the DTO fields to the existing entity (avoiding manual set operations)
        existing.setValue(dailyParameterDTO.getValue());
        existing.setDate(dailyParameterDTO.getDate());

        // Set ParameterType if applicable (make sure this field is properly handled in DTO)
        if (dailyParameterDTO.getParameterType() != null) {
            // Assuming a method exists in your mapper to fetch ParameterType based on DTO (if needed)
            ParameterType parameterType = parameterTypeRepository.findById(dailyParameterDTO.getParameterType())
                    .orElseThrow(() -> new NotFoundException("ParameterType not found for ID: " + dailyParameterDTO.getParameterType()));
            existing.setParameterType(parameterType);
        }

        // Save and return the updated entity
        return dailyParameterRepository.save(existing);
    }
    @Override
    public List<DailyParameterDTO> findByParameterTypeId(Long id) {
        try {
            List<DailyParameter> dailyParameters = dailyParameterRepository.findByParameterTypeId(id);

            return dailyParameters.stream()
                    .map(dailyParameterMapper::toDTO)
                    .collect(Collectors.toList());

        }  catch (Exception e) {
        e.printStackTrace(); // Full error stack trace
        return new ArrayList<>();
    }
    }

}