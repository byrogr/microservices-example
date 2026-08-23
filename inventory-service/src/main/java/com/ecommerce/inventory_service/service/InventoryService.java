package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.model.dto.InventoryRequestDto;
import com.ecommerce.inventory_service.model.dto.InventoryResponseDto;
import com.ecommerce.inventory_service.model.entity.Inventory;

import java.util.List;

public interface InventoryService {
    InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto);
    List<InventoryResponseDto> getAllInventories();
    InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto);
    void deleteInventory(Long id);
    boolean isInStock(String sku, Integer quantity);
}
