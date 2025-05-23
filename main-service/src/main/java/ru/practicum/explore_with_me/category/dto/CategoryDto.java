package ru.practicum.explore_with_me.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {

    //id — уникальный идентификатор категории
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    //name — название категории
    @NotBlank(message = "Название категории должно быть указано")
    @Size(min = 1, max = 50, message = "Длина названия категории должна быть от 1 до 50 символов")
    String name;
}
