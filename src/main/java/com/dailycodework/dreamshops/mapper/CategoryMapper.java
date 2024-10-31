package com.dailycodework.dreamshops.mapper;

import com.dailycodework.dreamshops.dto.CategoryDto;
import com.dailycodework.dreamshops.model.Category;

public class CategoryMapper {

    public static CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
