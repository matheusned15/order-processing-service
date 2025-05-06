package com.example.ecommerce.order_processing_service.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderEntity {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    private UUID customerId;
    private BigDecimal totalAmount;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items;
}
