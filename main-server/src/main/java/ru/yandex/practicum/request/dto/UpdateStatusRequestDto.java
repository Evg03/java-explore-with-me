package ru.yandex.practicum.request.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.request.model.Status;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateStatusRequestDto {
    @NotEmpty
    @NotNull
    private List<Integer> requestIds;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
}
