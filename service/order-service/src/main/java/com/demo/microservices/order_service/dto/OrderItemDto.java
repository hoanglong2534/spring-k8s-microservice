package com.demo.microservices.order_service.dto;

import com.demo.microservices.order_service.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    
    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return new OrderItemDto(
            orderItem.getId(),
            orderItem.getProductId(),
            orderItem.getProductName(),
            orderItem.getQuantity(),
            orderItem.getUnitPrice(),
            orderItem.getTotalPrice()
        );
    }
}



