package com.example.ecommerce.order_processing_service.adapter.in.rest;

import com.example.ecommerce.order_processing_service.application.dto.OrderRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.OrderResponseDTO;
// Aqui o import CORRETO para onde sua interface realmente está:
import com.example.ecommerce.order_processing_service.application.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders") // gera um construtor com orderService como parâmetro
public class OrderController {

    private final IOrderService orderService;  // sem @Autowired

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/{orderId}")
    public Mono<OrderResponseDTO> getOrder(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }
}
