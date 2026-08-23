package com.ecommerce.product_service.exception;

import lombok.Getter;

@Getter
public class ResourceNotFound extends RuntimeException {
    private final String resourceName, fieldName;
    private final Object fieldValue;

    public ResourceNotFound(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
