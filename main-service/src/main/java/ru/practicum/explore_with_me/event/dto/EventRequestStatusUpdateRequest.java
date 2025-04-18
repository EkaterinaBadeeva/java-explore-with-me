package ru.practicum.explore_with_me.event.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.request.model.ParticipationRequestState;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {
    //Изменение статуса запроса на участие в событии текущего пользователя
    @NotEmpty
    //requestIds — идентификаторы запросов на участие в событии текущего пользователя
    List<Long> requestIds;

    //status — новый статус запроса на участие в событии текущего пользователя
    @NotNull
    ParticipationRequestState status;
}
