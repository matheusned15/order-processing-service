package com.example.ecommerce.order_processing_service.adapter.out.messaging;


import com.example.ecommerce.order_processing_service.adapter.out.storage.S3InvoiceStorageAdapter;
import com.example.ecommerce.order_processing_service.common.event.PaymentCompletedEvent;
import com.example.ecommerce.order_processing_service.domain.model.Invoice;
import com.example.ecommerce.order_processing_service.domain.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final S3InvoiceStorageAdapter storageAdapter;
    private final InvoiceService invoiceService;

    @KafkaListener(topics = "orders.paid", containerFactory = "kafkaListenerContainerFactory")
    public void onPaymentCompleted(ConsumerRecord<String, PaymentCompletedEvent> record) {
        PaymentCompletedEvent evt = record.value();

        // 1) Gerar PDF (aqui um stub simples)
        byte[] pdf = generateInvoicePdf(evt.getOrderId(), evt.getAmount());

        // 2) Upload para S3
        String s3Key = storageAdapter.uploadInvoice(evt.getOrderId().toString(), pdf);

        // 3) Persistir Invoice
        Invoice invoice = Invoice.builder()
                .orderId(UUID.fromString(evt.getOrderId().toString()))
                .invoiceNumber("INV-" + UUID.randomUUID())
                .s3Key(s3Key)
                .issuedAt(Instant.now())
                .build();
        invoiceService.create(invoice);
    }

    private byte[] generateInvoicePdf(UUID orderId, BigDecimal amount) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // 1. Cria uma página
            PDPage page = new PDPage();
            document.addPage(page);

            // 2. Inicia o content stream
            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                // Cabeçalho
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 18);
                content.newLineAtOffset(50, 750);
                content.showText("Nota Fiscal");
                content.endText();

                // Dados do pedido
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, 720);
                content.showText("Pedido ID: " + orderId);
                content.newLineAtOffset(0, -15);
                content.showText("Valor Total: R$ " +
                        amount.setScale(2, BigDecimal.ROUND_HALF_UP));
                content.newLineAtOffset(0, -15);
                content.showText("Data de Emissão: " +
                        DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
                content.endText();

                // Rodapé
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                content.newLineAtOffset(50, 50);
                content.showText("Obrigado pela preferência!");
                content.endText();
            }

            // 3. Salva no ByteArrayOutputStream
            document.save(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar PDF da invoice", e);
        }
    }
}
