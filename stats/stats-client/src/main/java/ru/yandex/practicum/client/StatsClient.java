package ru.yandex.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.practicum.stats.dto.HitDto;
import ru.yandex.practicum.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatsClient {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final RestTemplate restTemplate;

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .detectRequestFactory(false)
                .build();
    }

    public ResponseEntity<HitDto> createHit(String app, String uri, String ip, LocalDateTime timestamp) {
        HitDto hitDto = new HitDto(app, uri, ip, timestamp);
        return restTemplate.exchange("/hit", HttpMethod.POST, new HttpEntity<>(hitDto), HitDto.class);
    }

    public ResponseEntity<StatsDto[]> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        StringBuilder sb = new StringBuilder();
        sb.append("/stats?")
                .append("start=").append(start.format(dateTimeFormatter))
                .append("&end=").append(end.format(dateTimeFormatter));
        if (uris != null) {
            for (String uri : uris) {
                sb.append("&uris=").append(uri);
            }
        }
        sb.append("&unique=").append(unique);
        return restTemplate.getForEntity(sb.toString(), StatsDto[].class);
    }
}
