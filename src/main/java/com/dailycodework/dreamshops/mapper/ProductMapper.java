package com.dailycodework.dreamshops.mapper;

import com.dailycodework.dreamshops.dto.CategoryDto;
import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.model.Product;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .images(product.getImages() == null || product.getImages().isEmpty()
                        ? null
                        : product.getImages().stream()
                        .map(image -> new ImageDto(image.getId(), image.getFileName(), image.getDownloadUrl()))
                        .collect(Collectors.toList()))

                .build();
    }
}
