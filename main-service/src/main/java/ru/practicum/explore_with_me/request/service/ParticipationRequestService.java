package ru.practicum.explore_with_me.request.service;

import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.user.model.User;

import java.util.List;

public interface ParticipationRequestService {
    ParticipationRequestDto create(Long eventId, Long userId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getParticipationRequest(Long userId);

    List<ParticipationRequestDto> findParticipationRequests(List<Long> requestIds);

    void updateStateRequests(List<ParticipationRequestDto> requestsDto, Event event, User user);
}
