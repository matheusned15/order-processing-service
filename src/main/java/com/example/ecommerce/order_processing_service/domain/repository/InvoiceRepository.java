package com.example.ecommerce.order_processing_service.domain.repository;

import com.example.ecommerce.order_processing_service.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    @Query("SELECT i FROM Invoice i WHERE i.orderId = cast(:orderId AS java.util.UUID)")
    Optional<Invoice> findByOrderId(@Param("orderId") String orderId);
}
