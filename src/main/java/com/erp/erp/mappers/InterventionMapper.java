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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterventionMapper {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final PieceRepository pieceRepository;

    // Convert Entity to DTO
    public InterventionDTO toDTO(Intervention intervention) {
        if (intervention == null) {
            return null;
        }

        InterventionDTO dto = new InterventionDTO();
        dto.setId((long) intervention.getId());
        dto.setTitle(intervention.getTitle());

        if (intervention.getEquipment() != null) {
            dto.setEquipmentId(intervention.getEquipment().getId());
        }

        if (intervention.getCreatedBy() != null) {
            dto.setCreatedBy(intervention.getCreatedBy().getId());
        }

        if (intervention.getApprovedBy() != null) {
            dto.setApprovedBy(intervention.getApprovedBy().getId());
        }

        if (intervention.getStaff() != null) {
            dto.setStaffIds(
                    intervention.getStaff().stream()
                            .map(User::getId)
                            .collect(Collectors.toList())
            );
        }

        if (intervention.getPieces() != null) {
            dto.setPieces(
                    intervention.getPieces().stream()
                            .map(Piece::getId)
                            .collect(Collectors.toList())
            );
        }

        dto.setStatus(intervention.getStatus());
        dto.setType(intervention.getType());
        dto.setDescription(intervention.getDescription());
        dto.setCreatedAt(intervention.getCreatedAt());
        dto.setUpdatedAt(intervention.getUpdatedAt());

        if (intervention.getUpdatedBy() != null) {
            dto.setUpdatedBy(intervention.getUpdatedBy().getId());
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
        intervention.setId(dto.getId() != null ? dto.getId().intValue() : 0);
        intervention.setTitle(dto.getTitle());

        if (dto.getEquipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + dto.getEquipmentId()));
            intervention.setEquipment(equipment);
        }

        if (dto.getCreatedBy() != null) {
            User createdBy = userRepository.findById(dto.getCreatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id (createdBy): " + dto.getCreatedBy()));
            intervention.setCreatedBy(createdBy);
        }

        if (dto.getApprovedBy() != null) {
            User approvedBy = userRepository.findById(dto.getApprovedBy())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id (approvedBy): " + dto.getApprovedBy()));
            intervention.setApprovedBy(approvedBy);
        }

        if (dto.getStaffIds() != null && !dto.getStaffIds().isEmpty()) {
            List<User> staff = userRepository.findAllById(dto.getStaffIds());
            intervention.setStaff(staff);
        }

        if (dto.getPieces() != null && !dto.getPieces().isEmpty()) {
            List<Piece> pieces = pieceRepository.findAllById(dto.getPieces());
            intervention.setPieces(pieces);
        }

        intervention.setStatus(dto.getStatus());
        intervention.setType(dto.getType());
        intervention.setDescription(dto.getDescription());
        intervention.setCreatedAt(dto.getCreatedAt());
        intervention.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getUpdatedBy() != null) {
            User updatedBy = userRepository.findById(dto.getUpdatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id (updatedBy): " + dto.getUpdatedBy()));
            intervention.setUpdatedBy(updatedBy);
        }

        intervention.setPriority(dto.getPriority());

        return intervention;
    }
}

