package ru.practicum.explore_with_me.rating.service;

import ru.practicum.explore_with_me.rating.dto.RatingDto;
import ru.practicum.explore_with_me.rating.dto.RatingStateResult;
import ru.practicum.explore_with_me.rating.model.RatingState;

public interface RatingService {
    RatingDto createRatingPrivate(Long userId, Long eventId, RatingState state);

    RatingDto updateRatingPrivate(Long userId, Long eventId, RatingState state);

    void deleteRatingPrivate(Long userId, Long eventId);

    RatingStateResult getRatingOfEventPrivate(Long userId, Long eventId);
}
