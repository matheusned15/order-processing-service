package com.example.ecommerce.order_processing_service.adapter.in.rest;
import com.example.ecommerce.order_processing_service.application.dto.InvoiceResponseDTO;
import com.example.ecommerce.order_processing_service.application.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders/{orderId}/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final IInvoiceService invoiceService;

    @GetMapping
    public Mono<InvoiceResponseDTO> getInvoice(@PathVariable String orderId) {
        return invoiceService.getInvoiceByOrderId(orderId);
    }
}
