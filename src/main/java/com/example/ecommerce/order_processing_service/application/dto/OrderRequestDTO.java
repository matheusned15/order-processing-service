package com.example.ecommerce.order_processing_service.application.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private String customerId;
    private List<OrderItemDTO> items;
}
