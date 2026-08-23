package com.ecommerce.product_service.dataloader;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerce.product_service.model.entity.Product;
import com.ecommerce.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 10; i++) {
            var product = Product.builder()
                    .name("Product " + i)
                    .description("Description for Product " + i)
                    .price(BigDecimal.valueOf(i * 10.0))
                    .build();
            productRepository.save(product);
        }
    }
}
