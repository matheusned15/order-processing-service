package com.example.ecommerce.order_processing_service.adapter.out.persistence;

import com.example.ecommerce.order_processing_service.domain.model.Order;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {
    OrderEntity toEntity(Order domain);
    Order toDomain(OrderEntity entity);
    List<OrderEntity> toEntityList(List<Order> domains);
    List<Order> toDomainList(List<OrderEntity> entities);
}
