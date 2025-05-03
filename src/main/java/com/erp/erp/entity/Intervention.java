package com.erp.erp.entity;


import com.erp.erp.enums.Etat;
import com.erp.erp.enums.Priority;
import com.erp.erp.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intervention {
    @Id
    private int id;
    private String title;

    // Equipment involved in intervention
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    // The user who created the intervention
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // The user who approved the intervention
    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    private Etat status;

    @Enumerated(EnumType.STRING)
    private Type type;

    // List of users who are responsible for executing the intervention
    @ManyToMany
    @JoinTable(
            name = "intervention_staff",
            joinColumns = @JoinColumn(name = "intervention_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> staff; // Renamed from "users" to "staff" for clarity

    private String description;

    // Pieces required for the intervention
    @ManyToMany
    @JoinTable(
            name = "intervention_piece",
            joinColumns = @JoinColumn(name = "intervention_id"),
            inverseJoinColumns = @JoinColumn(name = "piece_id")
    )
    private List<Piece> pieces;

    private Date createdAt;
    private Date  updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
