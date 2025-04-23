package ru.practicum.explore_with_me.category.service;

import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto categoryDto);

    void deleteCategoryById(Long catId);

    CategoryDto updateCategory(NewCategoryDto categoryDto, Long catId);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
