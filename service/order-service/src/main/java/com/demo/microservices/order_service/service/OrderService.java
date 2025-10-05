package com.demo.microservices.order_service.service;

import com.demo.microservices.order_service.dto.OrderDto;
import com.demo.microservices.order_service.dto.OrderItemDto;
import com.demo.microservices.order_service.entity.Order;
import com.demo.microservices.order_service.entity.OrderItem;
import com.demo.microservices.order_service.repository.OrderRepository;
import com.demo.microservices.order_service.mapper.OrderMapper;
import com.demo.microservices.order_service.mapper.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        if (order.getStatus() == null) {
            order.setStatus(Order.OrderStatus.PENDING);
        }
        
        // Calculate total amount from order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (orderDto.getOrderItems() != null) {
            for (OrderItemDto itemDto : orderDto.getOrderItems()) {
                OrderItem orderItem = orderItemMapper.toEntity(itemDto);
                orderItem.setTotalPrice(itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
                orderItem.setOrder(order);
                totalAmount = totalAmount.add(orderItem.getTotalPrice());
            }
        }
        order.setTotalAmount(totalAmount);
        
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }
    
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }
    
    @Transactional(readOnly = true)
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }
    
    public OrderDto updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }
    
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}



