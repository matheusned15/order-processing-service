package com.example.ecommerce.order_processing_service.domain.repository;

import com.example.ecommerce.order_processing_service.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
