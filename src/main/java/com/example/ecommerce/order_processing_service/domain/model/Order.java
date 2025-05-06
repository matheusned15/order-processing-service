package com.example.ecommerce.order_processing_service.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;

    private double totalAmount;

    private LocalDateTime createdAt;

    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private String invoiceUrl;

    public void calculateTotalAmount() {
        if (items != null) {
            this.totalAmount = items.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
        } else {
            this.totalAmount = 0.0;
        }
    }
}

