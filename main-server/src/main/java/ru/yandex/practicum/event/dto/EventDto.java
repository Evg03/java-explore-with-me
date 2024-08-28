package ru.yandex.practicum.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.event.model.Location;
import ru.yandex.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class EventDto {
    private final Integer id;
    private final String annotation;
    private final Integer category;
    private final Integer confirmedRequests;
    private final LocalDateTime createdOn;
    private final String description;
    private final LocalDateTime eventDate;
    private final UserShortDto initiator;
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final LocalDateTime publishedOn;
    private final Boolean requestModeration;
    private final String state;
    private final String title;
    private final Integer views;
}
