package ru.yandex.practicum.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventShortDto {
    private final Integer id;
    private final String annotation;
    private final Integer category;
    private final Integer confirmedRequests;
    private final UserShortDto initiator;
    private final LocalDateTime eventDate;
    private final Boolean paid;
    private final String title;
    private final Integer views;
}
