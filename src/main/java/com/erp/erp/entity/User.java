package com.erp.erp.entity;


import com.erp.erp.enums.Poste;
import com.erp.erp.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@SuperBuilder
public class User {

    @Id
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone Number is required")
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @NotBlank(message = "Surname is required")
    private String surname;

    @Temporal(TemporalType.DATE)
    private Date naissance;

    @NotNull
    @Column(unique = true)
    private Long cin;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Poste poste;

    private String categorie;
    private String adresse;

    // List of interventions created by this user
    @OneToMany(mappedBy = "createdBy")
    private List<Intervention> createdInterventions;

    // List of interventions approved by this user
    @OneToMany(mappedBy = "approvedBy")
    private List<Intervention> approvedInterventions;

    // List of interventions where this user is executing
    @ManyToMany(mappedBy = "staff")
    private List<Intervention> assignedInterventions;

    private final Date createdAt = new Date();

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
