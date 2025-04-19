package ru.practicum.explore_with_me.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.model.ParticipationRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipationRequestMapper {

    public static ParticipationRequestDto mapToParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getState())
                .build();
    }
}
