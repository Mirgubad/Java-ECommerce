package com.dailycodework.dreamshops.service.category;

import com.dailycodework.dreamshops.dto.CategoryDto;
import com.dailycodework.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategory(Long id);
}
