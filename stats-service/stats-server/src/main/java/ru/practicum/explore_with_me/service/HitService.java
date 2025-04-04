package ru.practicum.explore_with_me.service;

import ru.practicum.explore_with_me.HitDto;
import ru.practicum.explore_with_me.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface HitService {

    HitDto saveHit(HitDto hitDto);

    Collection<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
