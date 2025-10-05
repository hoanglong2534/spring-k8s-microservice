package com.demo.microservices.order_service.mapper;

import com.demo.microservices.order_service.dto.OrderDto;
import com.demo.microservices.order_service.entity.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class })
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(OrderDto dto, @MappingTarget Order entity);
}


