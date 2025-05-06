package com.example.ecommerce.order_processing_service.domain.service.impl;

import com.example.ecommerce.order_processing_service.adapter.out.messaging.KafkaProducerAdapter;
import com.example.ecommerce.order_processing_service.application.dto.PaymentRequestDTO;
import com.example.ecommerce.order_processing_service.application.dto.PaymentResponseDTO;
import com.example.ecommerce.order_processing_service.application.mapper.PaymentMapper;
import com.example.ecommerce.order_processing_service.common.event.PaymentCompletedEvent;
import com.example.ecommerce.order_processing_service.domain.model.Payment;
import com.example.ecommerce.order_processing_service.domain.repository.PaymentRepository;
import com.example.ecommerce.order_processing_service.domain.service.IPaymentService;
import com.example.ecommerce.order_processing_service.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentService domainPaymentService;
    private final PaymentRepository paymentRepository;
    private final KafkaProducerAdapter kafkaProducer;

    @Override
    public Mono<PaymentResponseDTO> processPayment(PaymentRequestDTO dto) {
        return Mono.fromCallable(() -> {
                    // 1) DTO → modelo de domínio
                    Payment payment = PaymentMapper.toModel(dto);
                    // 2) lógica de domínio: ID + save
                    return domainPaymentService.process(payment);
                })
                // 3) publicar evento após persistir
                .doOnNext(saved -> {
                    var event = new PaymentCompletedEvent(
                            UUID.fromString(saved.getId()),
                            UUID.fromString(saved.getOrderId()),
                            saved.getAmount(),
                            saved.getMethod().name(),
                            Instant.now()
                    );
                    kafkaProducer.publishPaymentCompleted(event);
                })
                // 4) mapear modelo → DTO de resposta
                .map(PaymentMapper::toResponseDTO);
    }

    @Override
    public Mono<PaymentResponseDTO> getPaymentById(String paymentId) {
        // wrap no Optional retornado pelo JPA em um Mono
        return Mono.justOrEmpty(paymentRepository.findById(paymentId))
                .switchIfEmpty(Mono.error(new RuntimeException("Pagamento não encontrado")))
                .map(PaymentMapper::toResponseDTO);
    }


    public Payment process(Payment payment) {
        // Gera um UUID para o pagamento
        payment.setId(UUID.randomUUID().toString());
        // Persiste no banco (JPA)
        return paymentRepository.save(payment);
    }
}
