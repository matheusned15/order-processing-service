package com.example.ecommerce.order_processing_service.domain.service;


import com.example.ecommerce.order_processing_service.domain.model.Invoice;
import com.example.ecommerce.order_processing_service.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public Invoice create(Invoice invoice) {
        invoice.setId(UUID.fromString(UUID.randomUUID().toString()));
        return invoiceRepository.save(invoice);
    }
}
