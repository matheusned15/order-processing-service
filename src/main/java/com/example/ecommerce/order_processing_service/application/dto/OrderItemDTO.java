package com.example.ecommerce.order_processing_service.application.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private String productId;
    private String productName;
    private int quantity;
    private double price;
}
