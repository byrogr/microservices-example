package com.ecommerce.order_service.service;

import com.ecommerce.order_service.exception.ResourceNotFoundException;
import com.ecommerce.order_service.model.dto.OrderRequestDto;
import com.ecommerce.order_service.model.dto.OrderResponseDto;
import com.ecommerce.order_service.model.mapper.OrderMapper;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        log.info("** Creating new order **");

        var order = orderMapper.toOrderEntity(orderRequestDto);
        order.setOrderNumber(UUID.randomUUID().toString());
        var savedOrder = orderRepository.save(order);

        log.info("** Create new order with number: {} successfully **", savedOrder.getOrderNumber());
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        log.info("** Retrieving all orders **");
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        log.info("** Retrieving order with ID: {} **", id);

        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        log.info("** Deleting order with ID: {} **", id);

        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", "id", id);
        }

        orderRepository.deleteById(id);
        log.info("** Deleting order with ID: {} successfully **", id);
    }
}
