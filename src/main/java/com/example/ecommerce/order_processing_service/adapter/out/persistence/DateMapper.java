package com.example.ecommerce.order_processing_service.adapter.out.persistence;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateMapper {
    public Instant asInstant(LocalDateTime date) {
        return (date == null) ? null
                : date.atZone(ZoneId.systemDefault()).toInstant();
    }
    public LocalDateTime asLocalDateTime(Instant instant) {
        return (instant == null) ? null
                : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
