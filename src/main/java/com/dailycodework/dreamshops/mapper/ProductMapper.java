package com.dailycodework.dreamshops.mapper;

import com.dailycodework.dreamshops.dto.CategoryDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.model.Product;

public class ProductMapper {
    public  static ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(new CategoryDto(product.getCategory().getId(), product.getCategory().getName()))
                .images(product.getImages())
                .build();
    }
}
