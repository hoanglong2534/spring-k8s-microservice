package com.demo.microservices.order_service.mapper;

import com.demo.microservices.order_service.dto.OrderItemDto;
import com.demo.microservices.order_service.entity.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem item);

    List<OrderItemDto> toDtoList(List<OrderItem> items);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateEntityFromDto(OrderItemDto dto, @MappingTarget OrderItem entity);
}


