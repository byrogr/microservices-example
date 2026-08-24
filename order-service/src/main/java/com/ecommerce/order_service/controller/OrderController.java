package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.model.dto.OrderRequestDto;
import com.ecommerce.order_service.model.dto.OrderResponseDto;
import com.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        log.info("** Init creating new order **");
        return orderService.placeOrder(orderRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDto> getAllOrders() {
        log.info("** Init retrieving all orders **");
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        log.info("** Init retrieving order with ID: {} **", id);
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        log.info("** Deleting order with ID: {} **", id);
        orderService.deleteOrder(id);
    }
}
