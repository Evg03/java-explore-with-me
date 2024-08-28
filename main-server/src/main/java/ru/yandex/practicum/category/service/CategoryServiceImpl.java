package ru.yandex.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;
import ru.yandex.practicum.category.dto.UpdateCategoryDto;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.exception.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = modelMapper.map(newCategoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(int id, UpdateCategoryDto updateCategoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Категории с id = %s не существует.", id));
        }
        Category category = optionalCategory.get();
        category.setName(updateCategoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Категории с id = %s не существует.", id));
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        //TODO удалить
//        return categoryRepository.findAll(PageRequest.of(from, size, Sort.by("id"))).stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        return categoryRepository.findWithOffsetAndLimit(from, size);


    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Категории с id = %s не существует.", id));
        }
        return modelMapper.map(optionalCategory.get(), CategoryDto.class);
    }
}
