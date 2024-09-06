package ru.yandex.practicum.hit;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.InvalidDateRangeException;
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
    @Transactional
    public void createHit(HitDto hitDto) {
        Hit map = new ModelMapper().map(hitDto, Hit.class);
        hitRepository.save(map);
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, Optional<List<String>> uris, Boolean unique) {
        if (start.isAfter(end)) {
            throw new InvalidDateRangeException("Дата начала диапазона не может быть позже даты конца диапазона.");
        }
        if (uris.isPresent() && !uris.get().isEmpty()) {
            if (unique) {
                return hitRepository.getStatsByUrisByUniqueIp(start, end, uris.get());
            } else {
                return hitRepository.getStatsByUris(start, end, uris.get());
            }
        }
        if (unique) {
            return hitRepository.getAllStatsByUniqueIp(start, end);
        } else {
            return hitRepository.getAllStats(start, end);
        }
    }

}
