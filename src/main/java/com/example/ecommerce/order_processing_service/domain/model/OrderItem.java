package com.example.ecommerce.order_processing_service.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {
    @Id
    private String productId;
    private String productName;
    private int quantity;
    private double price;

}
