package com.ecommerce.order_service.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemRequestDto> orderItems;
}
