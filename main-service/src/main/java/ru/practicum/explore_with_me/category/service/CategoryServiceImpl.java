package ru.practicum.explore_with_me.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.category.dao.CategoryRepository;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.category.dto.NewCategoryDto;
import ru.practicum.explore_with_me.category.mapper.CategoryMapper;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.exceptions.CommonException;
import ru.practicum.explore_with_me.exceptions.NotFoundException;
import ru.practicum.explore_with_me.exceptions.ValidationException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto categoryDto) {
        log.info("Добавление новой категории.");
        Category category = CategoryMapper.mapToCategory(categoryDto);
        checkConditions(category);
        checkName(category);
        category = categoryRepository.save(category);

        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(NewCategoryDto categoryDto, Long catId) {
        log.info("Изменение категории.");
        checkId(catId);
        Category newCategory = CategoryMapper.mapToCategory(categoryDto);
        checkName(newCategory);
        Category oldCategory = findCategoryById(catId);
        oldCategory.setName(newCategory.getName());
        oldCategory = categoryRepository.save(oldCategory);

        return CategoryMapper.mapToCategoryDto(oldCategory);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        log.info("Получение информации о категориях.");
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage;
        categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.getContent()
                .stream()
                .map(CategoryMapper::mapToCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        log.info("Получение информации о категории по её идентификатору.");
        checkId(catId);
        Category category = findCategoryById(catId);
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        log.info("Удаление категории.");

        checkId(catId);

        categoryRepository.deleteById(catId);
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkName(Category category) {
        for (Category cat : categoryRepository.findAll()) {
            if (cat.getName().equals(category.getName())) {
                log.warn("Категория уже существует.");
                throw new CommonException("Категория с name = " + category.getName() + " уже существует");
            }
        }
    }

    private void checkConditions(Category category) {
        if (category.getName().isEmpty()) {
            log.warn("Задано пустое имя пользователя.");
            throw new ValidationException("Задано пустое имя пользователя");
        }
    }

    private Category findCategoryById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
    }
}
