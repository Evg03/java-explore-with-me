package ru.yandex.practicum.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateCommentDto {
    @NotBlank
    @Size(min = 1, max = 300)
    private String text;
}
