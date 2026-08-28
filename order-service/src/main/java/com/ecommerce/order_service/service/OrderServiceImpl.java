package com.ecommerce.order_service.service;

import com.ecommerce.order_service.exception.ResourceNotFoundException;
import com.ecommerce.order_service.model.dto.OrderRequestDto;
import com.ecommerce.order_service.model.dto.OrderResponseDto;
import com.ecommerce.order_service.model.mapper.OrderMapper;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.external.InventoryServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryServiceClient inventoryServiceClient;

    @Value("${app.order.enabled: true}")
    private boolean ordersEnabled;

    @Override
    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {

        if (!ordersEnabled) {
            log.warn("** Order rejected **");
            throw new RuntimeException("** Order Service On Maintenance. Try to later **");
        }

        log.info("** Creating new order **");

        var order = orderMapper.toOrderEntity(orderRequestDto);

        for (var item : order.getOrderItems()) {
            var sku = item.getSku();
            var qty = item.getQuantity();

            try {
                inventoryServiceClient.reduceStock(sku, qty);
            } catch (Exception ex) {
                log.error("** Error to reduce stock for product with SKU: {} -> {} **", sku, ex.getMessage());
                throw new IllegalArgumentException("** Error to place new order **");
            }
        }


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
