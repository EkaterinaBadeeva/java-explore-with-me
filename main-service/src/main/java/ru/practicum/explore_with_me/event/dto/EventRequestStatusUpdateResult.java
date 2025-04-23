package ru.practicum.explore_with_me.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateResult {
    //Результат подтверждения/отклонения заявок на участие в событии

    //confirmedRequests — подтвержденные заявоки на участие в событии
    List<ParticipationRequestDto> confirmedRequests;

    //rejectedRequests — отклоненные заявоки на участие в событии
    List<ParticipationRequestDto> rejectedRequests;
}
