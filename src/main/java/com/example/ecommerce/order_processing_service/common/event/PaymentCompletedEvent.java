package com.example.ecommerce.order_processing_service.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {
    private UUID paymentId;
    private UUID orderId;
    private BigDecimal amount;
    private String method;     // ex: CREDIT_CARD, PIX
    private Instant paidAt;
}
