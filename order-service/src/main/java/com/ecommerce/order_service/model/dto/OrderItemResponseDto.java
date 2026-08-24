package com.ecommerce.order_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {
    private String orderItemId;
    private String sku;
    private Integer quantity;
    private String price;
}
