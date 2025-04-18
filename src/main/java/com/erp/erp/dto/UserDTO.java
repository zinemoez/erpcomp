package com.erp.erp.dto;

import com.erp.erp.enums.Poste;
import com.erp.erp.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.google.type.DateTime;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Date naissance;
    private Long cin;
    private String departmentId;
    private Poste poste;
    private String categorie;
    private String phoneNumber;
    private UserRole role;
    private String adresse;
}
