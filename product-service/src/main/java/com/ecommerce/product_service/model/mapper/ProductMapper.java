package com.ecommerce.product_service.model.mapper;

import com.ecommerce.product_service.model.dto.ProductRequestDto;
import com.ecommerce.product_service.model.dto.ProductResponseDto;
import com.ecommerce.product_service.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(target = "price", expression = "java(product.getPrice().toString())")
    @Mapping(target = "productId", source = "id")
    ProductResponseDto toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductRequestDto productRequestDto, @MappingTarget Product product);
}
