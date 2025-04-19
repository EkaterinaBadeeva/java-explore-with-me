package ru.practicum.explore_with_me.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    //name — имя или логин пользователя
    @NotBlank
    @Size(min = 2, max = 250)
    String name;

    //id — уникальный идентификатор пользователя
    Long id;

    //email — адрес электронной почты
    @Email
    @NotNull
    @Size(min = 6, max = 254)
    String email;
}
