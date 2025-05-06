package com.example.ecommerce.order_processing_service.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDTO {

    private String orderId;
    private String customerName;
    private List<OrderItemDTO> items;
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private String invoiceUrl;
}
