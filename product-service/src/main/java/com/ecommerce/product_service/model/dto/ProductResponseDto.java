package com.ecommerce.product_service.model.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        String productId,
        String name,
        String description,
        String price
) {
}
