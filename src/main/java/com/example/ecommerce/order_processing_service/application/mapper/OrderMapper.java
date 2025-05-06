package com.example.ecommerce.order_processing_service.application.mapper;

import com.example.ecommerce.order_processing_service.application.dto.OrderItemDTO;
import com.example.ecommerce.order_processing_service.application.dto.OrderRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.OrderResponseDTO;
import com.example.ecommerce.order_processing_service.domain.model.Customer;
import com.example.ecommerce.order_processing_service.domain.model.Order;
import com.example.ecommerce.order_processing_service.domain.model.OrderItem;
import com.example.ecommerce.order_processing_service.domain.model.OrderStatus;

import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderRequestDTO dto, Customer customer) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCustomer(customer);
        order.setItems(dto.getItems().stream()
                .map(OrderMapper::toOrderItem)
                .collect(Collectors.toList()));
        order.setStatus(OrderStatus.CREATED);
        order.calculateTotalAmount();
        order.setCreatedAt(java.time.LocalDateTime.now());
        return order;
    }

    private static OrderItem toOrderItem(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setProductId(dto.getProductId());
        item.setProductName(dto.getProductName());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        return item;
    }

    public static OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setItems(order.getItems().stream()
                .map(OrderMapper::toOrderItemDTO)
                .collect(Collectors.toList()));
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setInvoiceUrl(order.getInvoiceUrl());
        return dto;
    }

    private static OrderItemDTO toOrderItemDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }
}
