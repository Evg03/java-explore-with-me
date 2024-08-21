package ru.yandex.practicum.hit;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.stats.dto.HitDto;
import ru.yandex.practicum.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
//@RequestMapping()
@RequiredArgsConstructor
public class HitController {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final HitService hitService;

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@RequestBody HitDto hitDto) {
        hitService.createHit(hitDto);
    }

    @GetMapping(path = "/stats")
    public List<StatsDto> getStats(@RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start, @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end, @RequestParam Optional<List<String>> uris, @RequestParam(defaultValue = "false") Boolean unique) {
        return hitService.getStats(start, end, uris, unique);
    }
}
