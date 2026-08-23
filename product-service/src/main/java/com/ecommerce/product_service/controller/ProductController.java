package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.model.dto.ProductRequestDto;
import com.ecommerce.product_service.model.dto.ProductResponseDto;
import com.ecommerce.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        log.info("** Init fetching all products **");
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable String productId) {
        log.info("** Init fetching product by id: {} **", productId);
        return productService.getProductById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        log.info("** Init creating product **");
        return productService.createProduct(productRequestDto);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto updateProduct(@PathVariable String productId, @RequestBody @Valid ProductRequestDto productRequestDto) {
        log.info("** Init updating product by id: {} **", productId);
        return productService.updateProduct(productId, productRequestDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable String productId) {
        log.info("** Init deleting product by id: {} **", productId);
        productService.deleteProductById(productId);
    }
}
