package com.demo.microservices.order_service.dto;

import com.demo.microservices.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private Order.OrderStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDto> orderItems;
    
    public static OrderDto fromEntity(Order order) {
        return new OrderDto(
            order.getId(),
            order.getUserId(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getShippingAddress(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            order.getOrderItems() != null ? 
                order.getOrderItems().stream()
                    .map(OrderItemDto::fromEntity)
                    .toList() : null
        );
    }
}



