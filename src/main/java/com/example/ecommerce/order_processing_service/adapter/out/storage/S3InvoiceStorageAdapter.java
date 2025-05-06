package com.example.ecommerce.order_processing_service.adapter.out.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3InvoiceStorageAdapter {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public String uploadInvoice(String orderId, byte[] pdfBytes) {
        String key = "invoices/" + orderId + ".pdf";
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("application/pdf")
                .build();
        s3Client.putObject(req, RequestBody.fromBytes(pdfBytes));
        return key;
    }
}
