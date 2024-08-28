package ru.yandex.practicum.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.event.model.Location;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class UpdateEventAdminRequest {
    private final String annotation;
    private final Integer category;
    private final String description;
    private final LocalDateTime eventDate;
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final String state;
    private final String stateAction;
    private final String title;
}
