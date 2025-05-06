package com.example.ecommerce.order_processing_service.application.mapper;



import com.example.ecommerce.order_processing_service.application.dto.PaymentRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.PaymentResponseDTO;
import com.example.ecommerce.order_processing_service.domain.model.Payment;
import com.example.ecommerce.order_processing_service.domain.model.PaymentMethod;
import com.example.ecommerce.order_processing_service.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentMapper {

    public static Payment toModel(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setOrderId(dto.getOrderId());
        payment.setAmount(BigDecimal.valueOf(dto.getAmount()));
        payment.setMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaidAt(LocalDateTime.now());
        return payment;
    }

    public static PaymentResponseDTO toResponseDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getId());
        dto.setOrderId(payment.getOrderId());
        dto.setAmount(payment.getAmount());
        dto.setMethod(payment.getMethod().name());
        dto.setStatus(payment.getStatus().name());
        dto.setPaidAt(payment.getPaidAt());
        return dto;
    }
}
