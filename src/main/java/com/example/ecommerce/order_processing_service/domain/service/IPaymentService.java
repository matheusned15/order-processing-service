package com.example.ecommerce.order_processing_service.domain.service;


import com.example.ecommerce.order_processing_service.application.dto.PaymentRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.PaymentResponseDTO;
import reactor.core.publisher.Mono;

public interface IPaymentService {
    Mono<PaymentResponseDTO> processPayment(PaymentRequestDTO dto);
    Mono<PaymentResponseDTO> getPaymentById(String paymentId);
}
