package com.example.ecommerce.order_processing_service.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}