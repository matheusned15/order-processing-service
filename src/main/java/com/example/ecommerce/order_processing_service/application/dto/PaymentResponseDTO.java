package com.example.ecommerce.order_processing_service.application.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private String method;
    private String status;
    private LocalDateTime paidAt;
}
