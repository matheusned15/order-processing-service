package com.example.ecommerce.order_processing_service.application.mapper.service;

import com.example.ecommerce.order_processing_service.application.dto.InvoiceResponseDTO;
import com.example.ecommerce.order_processing_service.application.service.IInvoiceService;
import com.example.ecommerce.order_processing_service.application.service.InvoiceMapper;
import com.example.ecommerce.order_processing_service.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InvoiceAppService implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;  // JPA
    private final InvoiceMapper mapper;

    @Override
    public Mono<InvoiceResponseDTO> getInvoiceByOrderId(String orderId) {
        return Mono.justOrEmpty(invoiceRepository.findByOrderId(orderId))
                .switchIfEmpty(Mono.error(new RuntimeException("Invoice não encontrada")))
                .map(mapper::toDto);
    }
}
