package com.example.ecommerce.order_processing_service.adapter.in.rest;
import com.example.ecommerce.order_processing_service.adapter.out.storage.S3InvoiceStorageAdapter;
import com.example.ecommerce.order_processing_service.application.dto.InvoiceResponseDTO;
import com.example.ecommerce.order_processing_service.application.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.time.Duration;

@RestController
@RequestMapping("/orders/{orderId}/invoice")
public class InvoiceController {

    private final IInvoiceService invoiceService;
    private final S3InvoiceStorageAdapter storageAdapter;

    // Construtor explícito, para não depender de Lombok aqui
    public InvoiceController(IInvoiceService invoiceService,
                             S3InvoiceStorageAdapter storageAdapter) {
        this.invoiceService = invoiceService;
        this.storageAdapter = storageAdapter;
    }

    @GetMapping
    public Mono<InvoiceResponseDTO> getInvoice(@PathVariable String orderId) {
        return invoiceService.getInvoiceByOrderId(orderId);
    }

    @GetMapping("/download")
    public Mono<URL> downloadInvoice(@PathVariable String orderId) {
        return invoiceService.getInvoiceByOrderId(orderId)
                .map(dto -> storageAdapter.generatePresignedUrl(
                        dto.getS3Key(),
                        Duration.ofMinutes(15)
                ));
    }
}
