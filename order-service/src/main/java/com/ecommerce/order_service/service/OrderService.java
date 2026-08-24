package com.ecommerce.order_service.service;

import com.ecommerce.order_service.model.dto.OrderRequestDto;
import com.ecommerce.order_service.model.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto getOrderById(Long id);
    void deleteOrder(Long id);
}
