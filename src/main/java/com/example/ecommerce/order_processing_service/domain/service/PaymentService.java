package com.example.ecommerce.order_processing_service.domain.service;

import com.example.ecommerce.order_processing_service.domain.model.Payment;
import com.example.ecommerce.order_processing_service.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;  // JpaRepository<Payment, String>

    /**
     * Cria (ou processa) um pagamento, atribuindo um novo ID
     * e salvando no repositório.
     */
    public Payment process(Payment payment) {
        // Gera um UUID para o pagamento
        payment.setId(UUID.randomUUID().toString());
        // Persiste no banco (JPA)
        return paymentRepository.save(payment);
    }
}
