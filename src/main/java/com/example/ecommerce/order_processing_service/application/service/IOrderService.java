package com.example.ecommerce.order_processing_service.application.service;

import com.example.ecommerce.order_processing_service.application.dto.OrderRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.OrderResponseDTO;
import reactor.core.publisher.Mono;

public interface IOrderService {

    Mono<OrderResponseDTO> createOrder(OrderRequestDTO orderRequestDTO);

    Mono<OrderResponseDTO> getOrderById(String orderId);
}
