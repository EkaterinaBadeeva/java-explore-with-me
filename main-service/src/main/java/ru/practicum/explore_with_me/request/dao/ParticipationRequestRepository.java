package ru.practicum.explore_with_me.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.request.model.ParticipationRequest;
import ru.practicum.explore_with_me.request.model.ParticipationRequestState;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Optional<ParticipationRequest> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<ParticipationRequest> findAllByEventIdAndState(Long eventId, ParticipationRequestState state);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long requesterId);

    List<ParticipationRequest> findAllByIdIn(List<Long> requestIds);
}
