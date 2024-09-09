package ru.yandex.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateCategoryDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
