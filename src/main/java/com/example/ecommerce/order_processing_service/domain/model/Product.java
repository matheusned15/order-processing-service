package com.example.ecommerce.order_processing_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;

    @Column(length = 1000)
    private String description;

    private BigDecimal price;

    private Integer stockQuantity;
}
