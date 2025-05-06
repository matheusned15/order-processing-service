package com.example.ecommerce.order_processing_service.domain.service;

import com.example.ecommerce.order_processing_service.domain.model.Order;
import com.example.ecommerce.order_processing_service.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Gera um novo ID para o pedido e persiste no banco.
     */
    public Order create(Order order) {
        order.setId(UUID.randomUUID().toString());
        return orderRepository.save(order);
    }

    /**
     * (Opcional) pode expor método de consulta, se desejar
     */
    public Order findById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}
