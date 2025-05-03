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
    private Long user;
    private List<Long> users;

    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

    private CategoryDTO category;
    private List<Long> categories;
    private ProductDTO product;
    private List<ProductDTO> products;
    private Long employee;
    private List<Long> employees;
    private EquipmentDTO equipment;
    private List<String> equipments;
    private DepartmentDTO department;
    private List<String> departments;
    private String intervention;
    private List<String> interventions;
    private String piece;
    private List<String> pieces;
    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;
    private final LocalDateTime timestamp = LocalDateTime.now();








}
