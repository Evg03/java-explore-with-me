package ru.yandex.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDto {
    private String app;
    private String uri;
    private long hits;
}
