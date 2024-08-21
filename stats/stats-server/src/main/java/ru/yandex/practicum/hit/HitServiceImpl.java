package ru.yandex.practicum.hit;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.stats.dto.HitDto;
import ru.yandex.practicum.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    public void createHit(HitDto hitDto) {
        Hit map = new ModelMapper().map(hitDto, Hit.class);
        hitRepository.save(map);
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, Optional<List<String>> uris, Boolean unique) {
        if (uris.isPresent()) {
            if (unique) return hitRepository.getStatsByUrisByUniqueIp(start, end, uris.get());
            else return hitRepository.getStatsByUris(start, end, uris.get());
        }
        if (unique) return hitRepository.getAllStatsByUniqueIp(start, end);
        else return hitRepository.getAllStats(start, end);
    }

}
