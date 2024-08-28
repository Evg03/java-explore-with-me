package ru.yandex.practicum.request.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ParticipationRequestDto {
    private final Integer id;
    private final Integer event;
    private final LocalDateTime created;
    private final Integer requester;
    private final String status;

}
