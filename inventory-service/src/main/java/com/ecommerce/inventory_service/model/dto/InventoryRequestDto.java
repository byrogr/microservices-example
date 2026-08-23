package com.ecommerce.inventory_service.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequestDto {
    @NotBlank(message = "SKU must not be blank")
    private String sku;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}
