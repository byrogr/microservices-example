package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.model.dto.InventoryRequestDto;
import com.ecommerce.inventory_service.model.dto.InventoryResponseDto;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String sku, @RequestParam("qty") Integer quantity) {
        log.info("** Init Checking inventory for SKU: {} **", sku);
        return inventoryService.isInStock(sku, quantity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> getAllInventory() {
        log.info("** Init Getting all inventory **");
        return inventoryService.getAllInventories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponseDto createInventory(@RequestBody @Valid InventoryRequestDto inventoryRequestDto) {
        log.info("** Init Creating inventory for SKU: {} **", inventoryRequestDto.getSku());
        return inventoryService.createInventory(inventoryRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDto updateInventory(@PathVariable Long id, @RequestBody @Valid InventoryRequestDto inventoryRequestDto) {
        log.info("** Init Updating inventory with ID: {} **", id);
        return inventoryService.updateInventory(id, inventoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long id) {
        log.info("** Init Deleting inventory with ID: {} **", id);
        inventoryService.deleteInventory(id);
    }
}
