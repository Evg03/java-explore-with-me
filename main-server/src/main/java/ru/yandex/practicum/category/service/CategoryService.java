package ru.yandex.practicum.category.service;

import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;
import ru.yandex.practicum.category.dto.UpdateCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(int id, UpdateCategoryDto updateCategoryDto);

    void deleteCategory(int id);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(int id);
}
