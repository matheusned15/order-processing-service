package com.example.ecommerce.order_processing_service.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {
    @Id
    private UUID id;
    private UUID orderId;
    private String invoiceNumber;
    private String s3Key;
    private Instant issuedAt;
}

