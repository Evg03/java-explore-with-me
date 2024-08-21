package ru.yandex.practicum.hit;

import ru.yandex.practicum.stats.dto.HitDto;
import ru.yandex.practicum.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HitService {

    void createHit(HitDto hitDto);

    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, Optional<List<String>> uris, Boolean unique);


}
