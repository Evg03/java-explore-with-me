package ru.yandex.practicum.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Location {
    private final float lat;
    private final float lon;
}
