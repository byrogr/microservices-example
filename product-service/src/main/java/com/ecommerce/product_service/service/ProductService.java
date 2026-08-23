package com.ecommerce.product_service.service;

import com.ecommerce.product_service.model.dto.ProductRequestDto;
import com.ecommerce.product_service.model.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    ProductResponseDto updateProduct(String productId, ProductRequestDto productRequestDto);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(String productId);
    void deleteProductById(String productId);
}
