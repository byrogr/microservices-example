package com.ecommerce.order_service.service.external;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.PutExchange;

public interface InventoryServiceClient {

    @PutExchange("/api/v1/inventory/stock/{sku}/reduce")
    void reduceStock(@PathVariable String sku, @RequestParam Integer qty);
}
