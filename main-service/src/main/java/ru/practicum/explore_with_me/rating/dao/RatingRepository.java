package ru.practicum.explore_with_me.rating.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.rating.model.Rating;
import ru.practicum.explore_with_me.rating.model.RatingState;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Boolean existsByUserIdAndEventId(Long userId, Long eventId);

    Optional<Rating> findByUserIdAndEventId(Long userId, Long eventId);

    List<Rating> findAllByEventIdAndState(Long eventId, RatingState state);

    List<Rating> findAllByEventId(Long eventId);

    List<List<Rating>> findAllByEventIdIn(List<Long> eventIds);
}
