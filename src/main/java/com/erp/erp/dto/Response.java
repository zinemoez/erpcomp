package com.erp.erp.dto;

import com.erp.erp.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    //generic
    private int status;
    private String message;
    //for login
    private String token;
    private UserRole role;
    private String expirationTime;

    //for pagination
    private Integer totalPages;
    private Long totalElements;

    //data output optional
    private UserDTO user;
    private List<UserDTO> users;

    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

    private CategoryDTO category;
    private List<CategoryDTO> categories;
    private ProductDTO product;
    private List<ProductDTO> products;
    private UserDTO employee;
    private List<UserDTO> employees;
    private EquipmentDTO equipment;
    private List<EquipmentDTO> equipments;
    private DepartmentDTO department;
    private List<DepartmentDTO> departments;
    private InterventionDTO intervention;
    private List<InterventionDTO> interventions;
    private PieceDTO piece;
    private List<PieceDTO> pieces;
    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;
    private final LocalDateTime timestamp = LocalDateTime.now();








}
