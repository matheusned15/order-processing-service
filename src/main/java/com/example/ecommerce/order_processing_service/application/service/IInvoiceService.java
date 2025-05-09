package com.example.ecommerce.order_processing_service.application.service;

import com.example.ecommerce.order_processing_service.application.dto.InvoiceResponseDTO;
import reactor.core.publisher.Mono;

public interface IInvoiceService {
    Mono<InvoiceResponseDTO> getInvoiceByOrderId(String orderId);
}
