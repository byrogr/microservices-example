package com.ecommerce.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerce.product_service.model.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
