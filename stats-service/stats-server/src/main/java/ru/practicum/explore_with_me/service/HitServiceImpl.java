package ru.practicum.explore_with_me.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ViewStatsDtoProjection;
import ru.practicum.explore_with_me.dao.HitRepository;
import ru.practicum.explore_with_me.HitDto;
import ru.practicum.explore_with_me.ViewStatsDto;
import ru.practicum.explore_with_me.exceptions.ValidationException;
import ru.practicum.explore_with_me.mapper.HitMapper;
import ru.practicum.explore_with_me.model.Hit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    @Transactional
    public HitDto saveHit(HitDto hitDto) {
        log.info("Добавление информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.");
        Hit hit = HitMapper.mapToHit(hitDto);
        hit = hitRepository.save(hit);
        return HitMapper.mapToHitDto(hit);
    }

    @Override
    public Collection<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Получение статистики по посещениям.");
        if (start.isAfter(end)) {
            throw new ValidationException("Указана дата и время начала диапазона позже чем " +
                    "дата и время конца диапазона за который нужно выгрузить статистику");
        }
        Collection<ViewStatsDtoProjection> viewsStats = List.of();

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEndAndUniqueIp(start, end);
            } else {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEnd(start, end);
            }
        } else {
            if (unique) {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEndAndUrisInAndUniqueIp(start, end, uris);
            } else {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEndAndUrisIn(start, end, uris);
            }
        }

        return viewsStats.stream().map(stat -> ViewStatsDto.builder()
                        .app(stat.getApp())
                        .uri(stat.getUri())
                        .hits(stat.getHits())
                        .build())
                        .toList();
    }
}