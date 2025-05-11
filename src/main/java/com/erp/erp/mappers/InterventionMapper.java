package com.erp.erp.mappers;

import com.erp.erp.dto.InterventionDTO;
import com.erp.erp.entity.Equipment;
import com.erp.erp.entity.Intervention;
import com.erp.erp.entity.Piece;
import com.erp.erp.entity.User;
import com.erp.erp.repository.EquipmentRepository;
import com.erp.erp.repository.PieceRepository;
import com.erp.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterventionMapper {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final PieceRepository pieceRepository;
    private final UserMapper userMapper;
    private final PieceMapper pieceMapper;
    private final EquipmentMapper equipmentMapper;

    // Convert Entity to DTO
    public InterventionDTO toDTO(Intervention intervention) {
        if (intervention == null) {
            return null;
        }

        InterventionDTO dto = new InterventionDTO();
        dto.setId(intervention.getId());
        dto.setTitle(intervention.getTitle());

        if (intervention.getEquipment() != null) {
            dto.setEquipmentId(equipmentMapper.toDTO(intervention.getEquipment()));
        }

        if (intervention.getCreatedBy() != null) {
            dto.setCreatedBy(userMapper.toUserDTO(intervention.getCreatedBy()));
        }

        if (intervention.getApprovedBy() != null) {
            dto.setApprovedBy(userMapper.toUserDTO(intervention.getApprovedBy()));
        }

        if (intervention.getStaff() != null) {
            dto.setStaffIds(
                    intervention.getStaff().stream()
                            .map(userMapper::toUserDTO)
                            .collect(Collectors.toList())
            );
        }

        if (intervention.getPieces() != null) {
            dto.setPieces(
                    intervention.getPieces().stream()
                            .map(pieceMapper::toPieceDTO)
                            .collect(Collectors.toList())
            );
        }

        dto.setStatus(intervention.getStatus());
        dto.setType(intervention.getType());
        dto.setDescription(intervention.getDescription());
        dto.setCreatedAt(intervention.getCreatedAt());
        dto.setUpdatedAt(intervention.getUpdatedAt());

        if (intervention.getUpdatedBy() != null) {
            dto.setUpdatedBy(userMapper.toUserDTO(intervention.getUpdatedBy()));
        }

        dto.setPriority(intervention.getPriority());

        return dto;
    }

    // Convert DTO to Entity
    public Intervention toEntity(InterventionDTO dto) {
        if (dto == null) {
            return null;
        }

        Intervention intervention = new Intervention();
        intervention.setId(dto.getId());
        intervention.setTitle(dto.getTitle());

        if (dto.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(dto.getEquipmentId().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + dto.getEquipmentId().getId()));
            intervention.setEquipment(equipment);
        }

        if (dto.getCreatedBy() != null) {
            User createdBy = userRepository.findById(dto.getCreatedBy().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id (createdBy): " + dto.getCreatedBy().getId()));
            intervention.setCreatedBy(createdBy);
        }

        if (dto.getApprovedBy() != null) {
            User approvedBy = userRepository.findById(dto.getApprovedBy().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id (approvedBy): " + dto.getApprovedBy().getId()));
            intervention.setApprovedBy(approvedBy);
        }

        if (dto.getStaffIds() != null && !dto.getStaffIds().isEmpty()) {
            List<User> staff = dto.getStaffIds().stream()
                    .map(userDTO -> userRepository.findById(userDTO.getId())
                            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userDTO.getId())))
                    .collect(Collectors.toList());
            intervention.setStaff(staff);
        }

        if (dto.getPieces() != null && !dto.getPieces().isEmpty()) {
            List<Piece> pieces = dto.getPieces().stream()
                    .map(pieceDTO -> pieceRepository.findById(pieceDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Piece not found with ID: " + pieceDTO.getId())))
                    .collect(Collectors.toList());
            intervention.setPieces(pieces);
        }

        intervention.setStatus(dto.getStatus());
        intervention.setType(dto.getType());
        intervention.setDescription(dto.getDescription());
        intervention.setCreatedAt(dto.getCreatedAt());
        intervention.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getUpdatedBy() != null) {
            User updatedBy = userRepository.findById(dto.getUpdatedBy().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id (updatedBy): " + dto.getUpdatedBy().getId()));
            intervention.setUpdatedBy(updatedBy);
        }

        intervention.setPriority(dto.getPriority());

        return intervention;
    }
}
