package ru.practicum.explore_with_me.rating.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.rating.dto.RatingDto;
import ru.practicum.explore_with_me.rating.dto.RatingShortDto;
import ru.practicum.explore_with_me.rating.model.Rating;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingMapper {

    public static RatingDto mapToRatingDto(Rating rating) {
        return RatingDto.builder()
                .event(rating.getEvent().getId())
                .user(rating.getUser().getId())
                .state(rating.getState())
                .build();
    }

    public static RatingShortDto mapToRatingShortDto(Rating rating) {
        return RatingShortDto.builder()
                .user(rating.getUser().getName())
                .state(rating.getState())
                .build();
    }
}
