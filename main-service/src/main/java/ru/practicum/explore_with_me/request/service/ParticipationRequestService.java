package ru.practicum.explore_with_me.request.service;

import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestService {
    ParticipationRequestDto create(Long eventId, Long userId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getParticipationRequest(Long userId);

    ParticipationRequest findParticipationRequestByUserIdAndEventId(Long userId, Long eventId);
}
