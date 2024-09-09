package ru.yandex.practicum.compilation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.event.dto.EventShortDto;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CompilationDto {
    private Integer id;
    private String title;
    private List<EventShortDto> events;
    private Boolean pinned;
}
