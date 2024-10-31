package com.dailycodework.dreamshops.service.category;

import com.dailycodework.dreamshops.dto.CategoryDto;
import com.dailycodework.dreamshops.exceptions.ConflictException;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private  final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow( ()-> new NotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories= categoryRepository.findAll();
        List<CategoryDto> categoryDtos= categories.stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c-> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(()->
                        new ConflictException("Category with name " + category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory->{
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(()->new NotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public void deleteCategory(Long id) {
    categoryRepository.findById(id)
            .ifPresentOrElse(categoryRepository::delete,()->{
                throw new NotFoundException("Category with id " + id + " not found");
            });
    }
}
