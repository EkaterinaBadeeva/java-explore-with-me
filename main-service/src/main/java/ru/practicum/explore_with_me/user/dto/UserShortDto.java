package ru.practicum.explore_with_me.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {
    //id — уникальный идентификатор пользователя
    Long id;

    //name — имя или логин пользователя
//  @NotBlank
    String name;
}
