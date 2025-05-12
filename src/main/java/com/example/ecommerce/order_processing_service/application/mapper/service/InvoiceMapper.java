package com.example.ecommerce.order_processing_service.application.mapper.service;

import com.example.ecommerce.order_processing_service.application.dto.InvoiceResponseDTO;
import com.example.ecommerce.order_processing_service.domain.model.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceResponseDTO toDto(Invoice inv) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setInvoiceId(inv.getId());
        dto.setOrderId(inv.getOrderId().toString());
        dto.setInvoiceNumber(inv.getInvoiceNumber());
        dto.setS3Key(inv.getS3Key());
        dto.setIssuedAt(inv.getIssuedAt());
        return dto;
    }

}
