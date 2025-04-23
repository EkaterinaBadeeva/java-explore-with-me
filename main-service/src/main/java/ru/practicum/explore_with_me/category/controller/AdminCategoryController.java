package ru.practicum.explore_with_me.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.category.service.CategoryService;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    //POST /admin/categories
    // добавить новую категорию
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody NewCategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    //PATCH /admin/categories/{catId}
    // измененить категорию
    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@Valid @RequestBody NewCategoryDto categoryDto,
                                      @PathVariable Long catId) {
        return categoryService.updateCategory(categoryDto, catId);
    }

    //DELETE /admin/categories/{catId}
    // удалить категорию по id
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategoryById(catId);
    }
}
