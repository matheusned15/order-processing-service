package com.example.ecommerce.order_processing_service.domain.service.impl;

import com.example.ecommerce.order_processing_service.adapter.out.messaging.KafkaProducerAdapter;
import com.example.ecommerce.order_processing_service.application.dto.OrderRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.OrderResponseDTO;
import com.example.ecommerce.order_processing_service.application.mapper.OrderMapper;

import com.example.ecommerce.order_processing_service.common.event.OrderCreatedEvent;
import com.example.ecommerce.order_processing_service.domain.model.Order;
import com.example.ecommerce.order_processing_service.domain.repository.OrderRepository;
import com.example.ecommerce.order_processing_service.application.service.IOrderService;
import com.example.ecommerce.order_processing_service.domain.service.OrderService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.example.ecommerce.order_processing_service.domain.repository.CustomerRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final KafkaProducerAdapter kafkaProducer;

    public OrderServiceImpl(CustomerRepository customerRepository, OrderService orderService, OrderRepository orderRepository, KafkaProducerAdapter kafkaProducer) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Mono<OrderResponseDTO> createOrder(OrderRequestDTO dto) {
        return Mono.justOrEmpty(customerRepository.findById(dto.getCustomerId()))
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente não encontrado")))
                .flatMap(customer -> Mono.fromCallable(() -> {
                                    // 1) DTO + customer → entidade de domínio
                                    Order order = OrderMapper.toEntity(dto, customer);
                                    // 2) lógica de domínio: ID + save
                                    return orderService.create(order);
                                })
                                // 3) publicar evento após persistir
                                .doOnNext(saved -> {
                                    var event = new OrderCreatedEvent(
                                            UUID.fromString(saved.getId()),
                                            UUID.fromString(saved.getId()),
                                            saved.getItems().stream()
                                                    .map(i -> new OrderCreatedEvent.OrderItemPayload(
                                                            UUID.fromString(i.getProductId()),
                                                            i.getQuantity(),
                                                            i.getPrice()
                                                    ))
                                                    .toList(),
                                            saved.getTotalAmount(),
                                            Instant.now()
                                    );
                                    kafkaProducer.publishOrderCreated(event);
                                })
                                // 4) mapear domínio → DTO de resposta
                                .map(OrderMapper::toResponseDTO)
                );
    }

    @Override
    public Mono<OrderResponseDTO> getOrderById(String orderId) {
        return Mono.justOrEmpty(orderRepository.findById(orderId))
                .switchIfEmpty(Mono.error(new RuntimeException("Pedido não encontrado")))
                .map(OrderMapper::toResponseDTO);
    }
}
