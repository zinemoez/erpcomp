package com.erp.erp.sevices.impl;

import com.erp.erp.dto.EquipmentDTO;
import com.erp.erp.dto.Response;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.Piece;
import com.erp.erp.exceptions.NotFoundException;
import com.erp.erp.mappers.EquipmentMapper;
import com.erp.erp.mappers.InterventionMapper;
import com.erp.erp.mappers.ParameterTypeMapper;
import com.erp.erp.mappers.PieceMapper;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.sevices.serv.EquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {
    private final ModelMapper modelMapper;
    private final EquipmentRepository equipmentRepository;
    private final PieceRepository pieceRepository;
    private final PieceMapper pieceMapper;
    private final InterventionMapper interventionMapper;
    private final EquipmentMapper equipmentMapper;
    private final ParameterTypeMapper parameterTypeMapper;

    @Override
    public EquipmentDTO addEquipment(EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentMapper.toEntity(equipmentDTO);

        if (equipmentDTO.getPieces() != null) {
            List<Piece> pieces = equipmentDTO.getPieces().stream()
                    .map(pieceDTO -> pieceRepository.findById(pieceDTO)
                            .orElseThrow(() -> new NotFoundException("piece Not Found")))
                    .collect(Collectors.toList());
            equipment.setPieces(pieces);
        }
        equipmentRepository.save(equipment);
        return equipmentDTO;
    }

    @Override
    public EquipmentDTO updateEquipment(String id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new RuntimeException("equipment not found with id"));
        equipment.setDateMiseEnService(equipmentDTO.getDateMiseEnService());
        equipment.setDescription(equipmentDTO.getDescription());
        equipment.setName(equipmentDTO.getName());

        if (equipmentDTO.getPieces() != null) {
            List<Piece> pieces = equipmentDTO.getPieces().stream()
                    .map(pieceDTO -> pieceRepository.findById(pieceDTO)
                            .orElseThrow(() -> new NotFoundException("piece Not Found")))
                    .collect(Collectors.toList());
            equipment.setPieces(pieces);
        }
        List<Integer> interventionDTOs = equipment.getInterventions().stream()
                .map(intervention -> interventionMapper.toDTO(intervention).getId())
                .collect(Collectors.toList());
        equipmentDTO.setInterventions(interventionDTOs);

        List<Long> parameterTypeDTOs = equipment.getParameterType().stream()
                .map(parameterType -> parameterTypeMapper.toDTO(parameterType).getId())
                .collect(Collectors.toList());
        equipmentDTO.setParameterTypes(parameterTypeDTOs);

        equipmentRepository.save(equipment);
        return equipmentDTO;

    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // Convert each Equipment entity to EquipmentDTO
        List<EquipmentDTO> equipmentDTOS = equipments.stream()
                .map(equipment -> {
                    // Convert Equipment entity to EquipmentDTO
                    EquipmentDTO equipmentDTO = equipmentMapper.toDTO(equipment);

                    // Map Piece entities to PieceDTOs
                    List<String> pieceDTOs = equipment.getPieces().stream()
                            .map(piece -> pieceMapper.toPieceDTO(piece).getId())
                            .collect(Collectors.toList());
                    equipmentDTO.setPieces(pieceDTOs);

                    // Map Intervention entities to InterventionDTOs
                    List<Integer> interventionDTOs = equipment.getInterventions().stream()
                            .map(intervention -> interventionMapper.toDTO(intervention).getId())
                            .collect(Collectors.toList());
                    equipmentDTO.setInterventions(interventionDTOs);

                    return equipmentDTO;
                })
                .collect(Collectors.toList());

        return equipmentDTOS;
    }


    @Override
    public EquipmentDTO getEquipmentById(String id) {
        // Fetch the Equipment entity by ID
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipment Not Found"));

        // Convert the Equipment entity to EquipmentDTO
        EquipmentDTO equipmentDTO = equipmentMapper.toDTO(equipment);

        // Map the related pieces to PieceDTOs if pieces exist
        if (equipment.getPieces() != null && !equipment.getPieces().isEmpty()) {
            List<String> pieceDTOs = equipment.getPieces().stream()
                    .map(piece -> pieceMapper.toPieceDTO(piece).getId())
                    .collect(Collectors.toList());
            equipmentDTO.setPieces(pieceDTOs);
        }

        // Map the related interventions to InterventionDTOs if interventions exist
        if (equipment.getInterventions() != null && !equipment.getInterventions().isEmpty()) {
            List<Integer> interventionDTOs = equipment.getInterventions().stream()
                    .map(intervention -> interventionMapper.toDTO(intervention).getId())
                    .collect(Collectors.toList());
            equipmentDTO.setInterventions(interventionDTOs);
        }
        if (equipment.getParameterType() != null && !equipment.getParameterType().isEmpty()) {
        List<Long> parameterTypeDTOs = equipment.getParameterType().stream()
                .map(parameterType -> parameterTypeMapper.toDTO(parameterType).getId())
                .collect(Collectors.toList());
        equipmentDTO.setParameterTypes(parameterTypeDTOs);}

        // Build and return the Response with the mapped EquipmentDTO
        return equipmentDTO;

    }

    @Override
    public List<EquipmentDTO> getEquipmentByDepartmentId(String id) {
        // Fetch the list of equipment related to the department
        List<Equipment> equipments = equipmentRepository.findEquipmentByServiceId(id);

        // If no equipment is found, throw an exception (you can also return an empty list if preferred)
        if (equipments.isEmpty()) {
            throw new NotFoundException("No equipment found for the given department ID");
        }

        // Map the list of Equipment entities to EquipmentDTOs
        List<EquipmentDTO> equipmentDTOs = equipments.stream()
                .map(equipment -> {
                    // Mapping the Equipment to EquipmentDTO
                    EquipmentDTO equipmentDTO = equipmentMapper.toDTO(equipment);

                    // If pieces are present, map them to PieceDTOs
                    if (equipment.getPieces() != null) {
                        List<String> pieceDTOs = equipment.getPieces().stream()
                                .map(piece -> pieceMapper.toPieceDTO(piece).getId())
                                .collect(Collectors.toList());
                        equipmentDTO.setPieces(pieceDTOs); // Set pieces in the DTO
                    }

                    // If interventions are present, map them to InterventionDTOs
                    if (equipment.getInterventions() != null) {
                        List<Integer> interventionDTOs = equipment.getInterventions().stream()
                                .map(intervention -> interventionMapper.toDTO(intervention).getId())
                                .collect(Collectors.toList());
                        equipmentDTO.setInterventions(interventionDTOs);// Set interventions in the DTO
                    }
                    if (equipment.getParameterType() != null ) {
                        List<Long> parameterTypeDTOs = equipment.getParameterType().stream()
                                .map(parameterType -> parameterTypeMapper.toDTO(parameterType).getId())
                                .collect(Collectors.toList());
                        equipmentDTO.setParameterTypes(parameterTypeDTOs);}

                    return equipmentDTO;
                })
                .collect(Collectors.toList());

        return equipmentDTOs;

    }


    @Override
    public Response deleteEquipment(String id) {
        equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("equipment Not Found"));

        equipmentRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("equipment successfully deleted")
                .build();
    }
}

