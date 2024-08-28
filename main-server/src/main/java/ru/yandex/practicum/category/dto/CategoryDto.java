package ru.yandex.practicum.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {
    private final Integer id;
    private final String name;
}
