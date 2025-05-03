package com.erp.erp.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "piece")
public class Piece {
    @Id
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Sku is required")
    @Column(unique = true)
    private String sku;
    @Positive(message = "Product price must be a positive value")
    private BigDecimal price;
    @Min(value = 0, message = "Stock quantity cannot be less than zero")
    private Long stockQuantity;
    private String description;
    private String imageUrl;
    private Date expiryDate;
    private Date updatedAt;
    private final Date createdAt = new Date();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment")
    private Equipment equipment;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}