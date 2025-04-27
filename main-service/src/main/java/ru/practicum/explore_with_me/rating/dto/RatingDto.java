package ru.practicum.explore_with_me.rating.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.rating.model.RatingState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingDto {

    //event — событие, которое оценил пользователь
    Long event;

    //user — пользователь, который поставил оценку
    Long user;

    //state — состояние оценки (like/dislike)
    RatingState state;
}
