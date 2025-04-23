package ru.practicum.explore_with_me.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.request.model.ParticipationRequestStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    //created — дата и время создания заявки (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    //event — идентификатор события
    Long event;

    //id — уникальный идентификатор заявки
    Long id;

    //requester — идентификатор пользователя, отправившего заявку
    Long requester;

    //status — статус заявки
    ParticipationRequestStatus status;
}
