package ru.yandex.practicum.event.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    List<Integer> requestIds;
    @NotNull
    String status;
}
