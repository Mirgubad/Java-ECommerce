package com.dailycodework.dreamshops.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int quantity;
    private CategoryDto category;
    private List<ImageDto> images;
}
