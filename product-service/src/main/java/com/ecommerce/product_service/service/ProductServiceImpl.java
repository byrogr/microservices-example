package com.ecommerce.product_service.service;

import com.ecommerce.product_service.exception.ResourceNotFoundException;
import com.ecommerce.product_service.model.dto.ProductRequestDto;
import com.ecommerce.product_service.model.dto.ProductResponseDto;
import com.ecommerce.product_service.model.mapper.ProductMapper;
import com.ecommerce.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        log.info("** Fetching all products **");
        var products = productRepository.findAll();
        if (!products.isEmpty()) {
            return products.stream()
                    .map(productMapper::toResponse)
                    .toList();
        }
        return List.of();
    }

    @Override
    public ProductResponseDto getProductById(String productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        log.info("** Creating product **");
        var product = productMapper.toEntity(productRequestDto);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponseDto updateProduct(String productId, ProductRequestDto productRequestDto) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        productMapper.updateEntityFromDto(productRequestDto, product);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProductById(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        productRepository.deleteById(productId);
    }
}
