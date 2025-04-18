package com.erp.erp.entity;


import com.erp.erp.enums.Poste;
import com.erp.erp.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest  {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Date naissance;
    private Long cin;
    private String departmentId;
    @Enumerated(EnumType.STRING)
    private Poste poste;
    private String categorie;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String adresse;

}
