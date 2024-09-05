package ru.yandex.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.practicum.stats.dto.HitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsClient extends BaseClient {
    private static final String API_PREFIX = "/hit";

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .detectRequestFactory(true)
                        .build()
        );
    }

    public ResponseEntity<Object> createHit(String app, String uri, String ip, LocalDateTime timestamp) {
        HitDto hitDto = new HitDto(app, uri, ip, timestamp);
        return post("", hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        StringBuilder sb = new StringBuilder();

        if (uris != null && !uris.isEmpty()) {
            if (sb.isEmpty()) {
                sb.insert(0,"?");
            }
            for (int i = 0; i < uris.size(); i++) {
                String uri = uris.get(i);
                if (i != 0) {
                    sb.append("&");
                }
                sb.append("uris=");
                sb.append(uri);
            }
        }
        if (sb.isEmpty()) {
            sb.insert(0,"?");
        } else {
            sb.append("&");
        }
        if (unique) {
            sb.append("unique=true");
        }
        String path = URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8);
        return get(path);
    }
}
