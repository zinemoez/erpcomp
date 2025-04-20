package com.erp.erp.sevices.impl;
import com.erp.erp.dto.InterventionDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.dto.UserDTO;
import com.erp.erp.entity.*;
import com.erp.erp.enums.Etat;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.repository.InterventionRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.repository.UserRepository;
import com.erp.erp.sevices.Imp.InterventionService;
import com.erp.erp.sevices.Imp.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public InterventionDTO addIntervention(InterventionDTO interventionDTO) {
        Intervention intervention = modelMapper.map(interventionDTO, Intervention.class);
        interventionRepository.save(intervention);
        return interventionDTO;
    }

    @Override
    public InterventionDTO updateIntervention(int id,InterventionDTO interventionDTO) {
        Intervention intervention = interventionRepository.findById(id).orElseThrow(() -> new RuntimeException("intervention not found with id" ));
        intervention.setPriority(interventionDTO.getPriority());
        if(Objects.equals(userService.getCurrentLoggedInUser().getId(), interventionDTO.getApprovedBy().getId()))
        {intervention.setStatus(interventionDTO.getStatus());}
        UserDTO userDTO=userService.getCurrentLoggedInUser();
        User user=modelMapper.map(userDTO, User.class);
        intervention.setUpdatedBy(user);
        intervention.setUpdatedAt(LocalDateTime.now());
        intervention.setTitle(intervention.getTitle());
        intervention.setCreatedBy(modelMapper.map(interventionDTO.getCreatedBy(), User.class));
        intervention.setApprovedBy(userRepository.findById(interventionDTO.getApprovedBy().getId()).orElseThrow(() -> new NotFoundException("Equipment not found with ID")));
        intervention.setDescription(interventionDTO.getDescription());
        intervention.setPieces(interventionDTO.getPieces());
        intervention.setStaff(interventionDTO.getStaffIds());
        interventionRepository.save(intervention);
        return interventionDTO;

    }

    @Override
    public   List<InterventionDTO>  getAllInterventions() {
        List<Intervention> interventions = interventionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));;

        List<InterventionDTO> interventionDTOS = modelMapper.map(interventions, new TypeToken<List<InterventionDTO>>() {}.getType());

        return interventionDTOS;
    }

    @Override
    public InterventionDTO getInterventionById(int id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Intervention Not Found"));
InterventionDTO interventionDTO=modelMapper.map(intervention, InterventionDTO.class);


        return interventionDTO;
    }

    @Override
    public List<InterventionDTO> getInterventionByEquipmentId(String id) {
        List<Intervention> interventions=interventionRepository.findByEquipmentId(id);
        List<InterventionDTO> interventionDTOS=modelMapper.map(interventions, new TypeToken<List<InterventionDTO>>() {}.getType());
    return interventionDTOS;
    }

    @Override
    public Response deleteIntervention(int  id) {
        interventionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("intervention Not Found"));

        interventionRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("intervention successfully deleted")
                .build();
    }

    @Override
    public Response ApproveIntervention(int id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de travail non trouvé"));
        if (intervention.getApprovedBy().getId().equals(userService.getCurrentLoggedInUser().getId()))
          intervention.setStatus(Etat.APPROUVE);
        interventionRepository.save(intervention);
        return Response.builder()
                .status(200)
                .message("intervention successfully approuved")
                .build();
    }

    @Override
    public List<InterventionDTO> getInterventionCreatedBy(Long id) {
        List<Intervention> interventions = interventionRepository.findByApprovedByIdOrderByCreatedAtDesc(id);
        return modelMapper.map(interventions, new TypeToken<List<InterventionDTO>>() {}.getType());
    }

    @Override
    public List<InterventionDTO> getInterventionApprouvedBy(Long id) {
        List<Intervention> interventions = interventionRepository.findByApprovedByIdOrderByCreatedAtDesc(id);
        return modelMapper.map(interventions, new TypeToken<List<InterventionDTO>>() {}.getType());


    }

    @Override
    public List<InterventionDTO> getInterventionByPieceId(String id) {
        List<Intervention> interventions = interventionRepository.findByPieceId(id);
        return modelMapper.map(interventions, new TypeToken<List<InterventionDTO>>() {}.getType());

    }
}
