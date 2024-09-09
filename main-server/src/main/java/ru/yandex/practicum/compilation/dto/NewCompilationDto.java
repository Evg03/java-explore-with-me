package ru.yandex.practicum.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder
public class NewCompilationDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    @Builder.Default
    private Boolean pinned = false;
    @Builder.Default
    private List<Integer> events = new ArrayList<>();
}
