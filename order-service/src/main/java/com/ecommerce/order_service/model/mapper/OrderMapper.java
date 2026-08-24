package com.ecommerce.order_service.model.mapper;

import com.ecommerce.order_service.model.dto.OrderItemRequestDto;
import com.ecommerce.order_service.model.dto.OrderItemResponseDto;
import com.ecommerce.order_service.model.dto.OrderRequestDto;
import com.ecommerce.order_service.model.dto.OrderResponseDto;
import com.ecommerce.order_service.model.entity.Order;
import com.ecommerce.order_service.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", expression = "java(UUID.randomUUID().toString())")
    Order toOrderEntity(OrderRequestDto orderRequestDto);

    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItemEntity(OrderItemRequestDto orderItemRequestDto);

    @Mapping(target = "orderId", expression = "java(order.getId().toString())")
    OrderResponseDto toOrderResponse(Order order);

    @Mapping(target = "orderItemId", expression = "java(orderItem.getId().toString())")
    OrderItemResponseDto toOrderItemResponse(OrderItem orderItem);
}
