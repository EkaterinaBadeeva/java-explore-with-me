package ru.practicum.explore_with_me.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.event.dto.EventShortDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {

    //events — список событий входящих в подборку
    List<EventShortDto> events;

    //id — уникальный идентификатор события
    @NotNull
    Long id;

    //pinned — закреплена ли подборка на главной странице сайта
    @NotNull
    Boolean pinned;

    //title — заголовок подборки
    @NotBlank(message = "Заголовок подборки должен быть указан")
    @Size(min = 1, max = 50, message = "Длина заголовока подборки должна быть от 1 до 50 символов")
    String title;
}
