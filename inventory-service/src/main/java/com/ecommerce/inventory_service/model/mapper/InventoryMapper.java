package com.ecommerce.inventory_service.model.mapper;

import com.ecommerce.inventory_service.model.dto.InventoryRequestDto;
import com.ecommerce.inventory_service.model.dto.InventoryResponseDto;
import com.ecommerce.inventory_service.model.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    Inventory toEntity(InventoryRequestDto inventoryRequestDto);

    @Mapping(target = "inventoryId", source = "id")
    @Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponseDto toResponse(Inventory inventory);
}
