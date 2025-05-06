package com.example.ecommerce.order_processing_service.domain.repository;

import com.example.ecommerce.order_processing_service.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
