package com.example.ecommerce.order_processing_service.adapter.out.messaging;


import com.example.ecommerce.order_processing_service.common.event.OrderCreatedEvent;
import com.example.ecommerce.order_processing_service.common.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerAdapter {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreated(OrderCreatedEvent event) {
    kafkaTemplate.send("orders.created", event.getOrderId().toString(), event);
    }

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        kafkaTemplate.send("orders.paid", event.getOrderId().toString(), event);
    }
}
