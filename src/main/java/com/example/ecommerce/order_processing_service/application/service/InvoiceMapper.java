package com.example.ecommerce.order_processing_service.application.service;

import com.example.ecommerce.order_processing_service.application.dto.InvoiceResponseDTO;
import com.example.ecommerce.order_processing_service.domain.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceResponseDTO toDto(Invoice invoice);
}
