package com.dailycodework.dreamshops.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public ProductDto(Long id, String name, String brand, String description, BigDecimal price, int quantity, CategoryDto category, List<ImageDto> images) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.images = images;
    }


    public ProductDto() {
    }

}
