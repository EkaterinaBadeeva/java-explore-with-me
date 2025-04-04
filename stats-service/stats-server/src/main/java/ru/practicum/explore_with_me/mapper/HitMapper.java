package ru.practicum.explore_with_me.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.HitDto;
import ru.practicum.explore_with_me.model.Hit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HitMapper {
    public static HitDto mapToHitDto(Hit hit) {
        return HitDto.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .build();
    }

    public static Hit mapToHit(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());

        return hit;
    }
}