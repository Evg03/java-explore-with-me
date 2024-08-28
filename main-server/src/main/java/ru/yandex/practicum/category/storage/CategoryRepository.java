package ru.yandex.practicum.category.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT new ru.yandex.practicum.category.dto.CategoryDto(cat.id, cat.name) " +
            "FROM Category AS cat " +
            "ORDER BY cat.name " +
            "OFFSET :offset " +
            "LIMIT :size")
    public List<CategoryDto> findWithOffsetAndLimit(int offset, int limit);
}
