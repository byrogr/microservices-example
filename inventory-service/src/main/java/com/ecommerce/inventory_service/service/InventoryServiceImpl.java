package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.ecommerce.inventory_service.model.dto.InventoryRequestDto;
import com.ecommerce.inventory_service.model.dto.InventoryResponseDto;
import com.ecommerce.inventory_service.model.mapper.InventoryMapper;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Value("${app.inventory.allow-backorders:false}")
    private boolean allowBackorders;

    @Override
    @Transactional
    public InventoryResponseDto createInventory(InventoryRequestDto inventoryRequestDto) {
        log.info("** Creating inventory with SKU: {} **", inventoryRequestDto.getSku());

        boolean exists = inventoryRepository.existsBySku(inventoryRequestDto.getSku());
        if (exists) {
            throw new IllegalArgumentException("** Inventory with SKU " + inventoryRequestDto.getSku() + " already exists. **");
        }

        var savedInventory = inventoryRepository.save(inventoryMapper.toEntity(inventoryRequestDto));

        log.info("** Inventory with SKU: {} created successfully **", inventoryRequestDto.getSku());
        return inventoryMapper.toResponse(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> getAllInventories() {
        log.info("** Retrieving all inventories **");

        return inventoryRepository.findAll().stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponseDto updateInventory(Long id, InventoryRequestDto inventoryRequestDto) {
        log.info("** Updating inventory with ID: {} **", id);

        var inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id));
        inventoryMapper.updateEntityFromDto(inventoryRequestDto, inventory);

        log.info("** Inventory with ID: {} updated successfully **", id);
        return inventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {
        log.info("** Deleting inventory with ID: {} **", id);

        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory", "id", id);
        }

        inventoryRepository.deleteById(id);
        log.info("** Inventory with ID: {} deleted successfully **", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {
        log.info("** Checking stock for SKU: {} with required quantity: {} **", sku, quantity);

        if (allowBackorders) {
            log.warn("** Backorder Mode On: Allow stock for SKU: {} **", sku);
            return true;
        }

        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public void reduceStock(String sku, Integer quantity) {
        log.info("** Reducing stock to product with sku: {}  **", sku);
        var inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "sku", sku));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("** Insufficient Stock **");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
        log.info("** Reducing stock to product with sku: {} successfully **", sku);
    }
}
