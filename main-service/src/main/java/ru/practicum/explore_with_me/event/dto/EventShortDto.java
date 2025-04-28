package ru.practicum.explore_with_me.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.category.dto.CategoryDto;
import ru.practicum.explore_with_me.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    //annotation — краткое описание события
    String annotation;

    //category — категории к которой относится событие
    CategoryDto category;

    //confirmedRequests — количество одобренных заявок на участие в данном событии
    Integer confirmedRequests;

    //eventDate — дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    //id — уникальный идентификатор события
    Long id;

    //initiator — пользователь (краткая информация)
    UserShortDto initiator;

    //paid — нужно ли оплачивать участие
    @Builder.Default
    Boolean paid = false;

    //title — заголовок
    String title;

    //views — количество просмотрев события
    Long views;

    //rating — рейтинг события
    @Builder.Default
    int rating = 0;

    //ratingInitiator — рейтинг пользователя
    int ratingInitiator;
}
