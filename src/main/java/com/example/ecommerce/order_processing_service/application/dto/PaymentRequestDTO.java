package com.example.ecommerce.order_processing_service.application.dto;


import lombok.Data;

@Data
public class              PaymentRequestDTO {

    private String orderId;
    private double amount;
    private String paymentMethod;
}
