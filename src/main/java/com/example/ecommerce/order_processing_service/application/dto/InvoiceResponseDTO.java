package com.example.ecommerce.order_processing_service.application.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class InvoiceResponseDTO {
    private String invoiceId;
    private String orderId;
    private String invoiceNumber;
    private String s3Key;
    private Instant issuedAt;

    public String getS3Key() {
        return s3Key;
    }
}
