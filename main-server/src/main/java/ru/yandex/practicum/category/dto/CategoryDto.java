package ru.yandex.practicum.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;
}
