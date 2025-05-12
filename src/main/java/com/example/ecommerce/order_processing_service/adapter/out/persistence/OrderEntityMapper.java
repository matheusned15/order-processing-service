package com.example.ecommerce.order_processing_service.adapter.out.persistence;

import com.example.ecommerce.order_processing_service.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {
    @Mapping(source = "createdAt", target = "createdAt")
    OrderEntity toEntity(Order order);
    @Mapping(source = "createdAt", target = "createdAt")
    Order toDomain(OrderEntity entity);
    List<OrderEntity> toEntityList(List<Order> domains);
    List<Order> toDomainList(List<OrderEntity> entities);

    default Instant map(LocalDateTime date) {
        return date == null
                ? null
                : date.atZone(ZoneId.systemDefault()).toInstant();
    }
    // método auxiliar para Instant → LocalDateTime
    default LocalDateTime map(Instant instant) {
        return instant == null
                ? null
                : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
