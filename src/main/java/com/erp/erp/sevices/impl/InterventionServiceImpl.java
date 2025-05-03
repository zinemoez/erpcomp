package com.erp.erp.sevices.impl;
import com.erp.erp.dto.InterventionDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.*;
import com.erp.erp.enums.Etat;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.mappers.InterventionMapper;
import com.erp.erp.mappers.UserMapper;
import com.erp.erp.repository.InterventionRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.repository.UserRepository;
import com.erp.erp.sevices.serv.InterventionService;
import com.erp.erp.sevices.serv.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class InterventionServiceImpl implements InterventionService {

    private final InterventionRepository interventionRepository;
    private final PieceRepository pieceRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final InterventionMapper interventionMapper;
    private final UserMapper userMapper;

    @Override
    public InterventionDTO addIntervention(InterventionDTO interventionDTO) {
        Intervention intervention = interventionMapper.toEntity(interventionDTO);
        interventionRepository.save(intervention);
        return interventionDTO;
    }

    @Override
    public InterventionDTO updateIntervention(int id, InterventionDTO interventionDTO) {
        // Fetch the existing intervention or throw an exception
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention not found with id"));

        // Set the status based on the current logged-in user
        if (Objects.equals(userService.getCurrentLoggedInUser().getId(), interventionDTO.getApprovedBy())) {
            intervention.setStatus(interventionDTO.getStatus());
        }

        // Save the updated intervention
        interventionRepository.save(intervention);

        return interventionDTO;
    }

    @Override
    public List<InterventionDTO> getAllInterventions() {
        // Fetch all interventions
        List<Intervention> interventions = interventionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // Map the list of interventions to DTOs using InterventionMapper
        return interventions.stream()
                .map(interventionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterventionDTO getInterventionById(int id) {
        // Fetch the intervention by ID
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Intervention Not Found"));

        // Map the intervention to DTO using InterventionMapper
        return interventionMapper.toDTO(intervention);
    }

    @Override
    public List<InterventionDTO> getInterventionByEquipmentId(String id) {
        // Fetch the interventions by equipment ID
        List<Intervention> interventions = interventionRepository.findByEquipmentId(id);

        // Map the list of interventions to DTOs using InterventionMapper
        return interventions.stream()
                .map(interventionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Response deleteIntervention(int id) {
        // Check if the intervention exists before deleting
        interventionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Intervention Not Found"));

        // Delete the intervention
        interventionRepository.deleteById(id);

        // Return response
        return Response.builder()
                .status(200)
                .message("Intervention successfully deleted")
                .build();
    }

    @Override
    public Response ApproveIntervention(int id) {
        // Fetch the intervention to approve
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention not found"));

        // Check if the current logged-in user is authorized to approve
        if (intervention.getApprovedBy().getId().equals(userService.getCurrentLoggedInUser().getId())) {
            intervention.setStatus(Etat.APPROUVE);
        }

        // Save the updated intervention
        interventionRepository.save(intervention);

        return Response.builder()
                .status(200)
                .message("Intervention successfully approved")
                .build();
    }

    @Override
    public List<InterventionDTO> getInterventionCreatedBy(Long id) {
        // Fetch interventions created by a specific user
        List<Intervention> interventions = interventionRepository.findByCreatedByIdOrderByCreatedAtDesc(id);

        // Map the list of interventions to DTOs using InterventionMapper
        return interventions.stream()
                .map(interventionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterventionDTO> getInterventionApprouvedBy(Long id) {
        // Fetch interventions approved by a specific user
        List<Intervention> interventions = interventionRepository.findByApprovedByIdOrderByCreatedAtDesc(id);

        // Map the list of interventions to DTOs using InterventionMapper
        return interventions.stream()
                .map(interventionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterventionDTO> getInterventionByPieceId(String id) {
        // Fetch interventions by piece ID
        List<Intervention> interventions = interventionRepository.findByPieceId(id);

        // Map the list of interventions to DTOs using InterventionMapper
        return interventions.stream()
                .map(interventionMapper::toDTO)
                .collect(Collectors.toList());
    }
}