package ru.yandex.practicum.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;


public interface HitRepository extends JpaRepository<Hit, Integer> {

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(h.app, h.uri, COUNT(h.uri)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp between :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.uri) DESC")
    List<StatsDto> getStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp between :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri, h.ip " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatsDto> getStatsByUrisByUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(h.app, h.uri, COUNT(h.uri)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp between :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.uri) DESC")
    List<StatsDto> getAllStats(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.yandex.practicum.stats.dto.StatsDto(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp between :start AND :end " +
            "GROUP BY h.app, h.uri , h.ip " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatsDto> getAllStatsByUniqueIp(LocalDateTime start, LocalDateTime end);
}
