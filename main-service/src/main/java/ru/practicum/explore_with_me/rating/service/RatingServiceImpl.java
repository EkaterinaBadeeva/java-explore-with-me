package ru.practicum.explore_with_me.rating.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.service.EventService;
import ru.practicum.explore_with_me.exceptions.ConflictException;
import ru.practicum.explore_with_me.exceptions.NotFoundException;
import ru.practicum.explore_with_me.exceptions.ValidationException;
import ru.practicum.explore_with_me.rating.dao.RatingRepository;
import ru.practicum.explore_with_me.rating.dto.RatingDto;
import ru.practicum.explore_with_me.rating.dto.RatingStateResult;
import ru.practicum.explore_with_me.rating.mapper.RatingMapper;
import ru.practicum.explore_with_me.rating.model.Rating;
import ru.practicum.explore_with_me.rating.model.RatingState;
import ru.practicum.explore_with_me.request.model.ParticipationRequest;
import ru.practicum.explore_with_me.request.service.ParticipationRequestService;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.explore_with_me.event.model.EventState.PUBLISHED;
import static ru.practicum.explore_with_me.request.model.ParticipationRequestStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final EventService eventService;
    private final ParticipationRequestService requestService;

    // поставить оценку событию
    // оценку можно поставить только если пользователь участвовал в событии
    // нельзя поставить больше одной оценки одному событию
    @Override
    @Transactional
    public RatingDto createRatingPrivate(Long userId, Long eventId, RatingState state) {
        log.info("Добавление оценки от текущего пользователя событию.");
        checkId(eventId);
        checkId(userId);
        checkRatingState(state);

        checkExistRating(userId, eventId);
        User user = findAndCheckUser(userId);
        Event event = findAndCheckEvent(eventId, userId);

        Rating rating = new Rating();
        rating.setCreated(LocalDateTime.now());
        rating.setEvent(event);
        rating.setUser(user);
        rating.setState(state);
        rating = ratingRepository.save(rating);

        return RatingMapper.mapToRatingDto(rating);
    }

    @Override
    @Transactional
    public RatingDto updateRatingPrivate(Long userId, Long eventId, RatingState state) {
        checkId(eventId);
        checkId(userId);

        Rating oldRating = findRating(userId, eventId);

        findAndCheckUser(userId);
        findAndCheckEvent(eventId, userId);

        if (oldRating.getState() != state) {
            checkRatingState(state);
            oldRating.setCreated(LocalDateTime.now());
            oldRating.setState(state);
        }

        return RatingMapper.mapToRatingDto(oldRating);
    }

    @Override
    @Transactional
    public void deleteRatingPrivate(Long userId, Long eventId) {
        log.info("Удаление оценки.");
        checkId(eventId);
        checkId(userId);

        Rating rating = findRating(userId, eventId);

        ratingRepository.deleteById(rating.getId());
    }

    @Override
    public RatingStateResult getRatingOfEventPrivate(Long userId, Long eventId) {
        log.info("Получение информации о всех оценках на событие текущего пользователя.");
        checkId(eventId);
        checkId(userId);
        findAndCheckUser(userId);
        Event event = eventService.findEventById(eventId);
        List<Rating> likes = new ArrayList<>();
        List<Rating> dislikes = new ArrayList<>();

        if (event.getInitiator().getId().equals(userId)) {
            likes = ratingRepository.findAllByEventIdAndState(eventId, RatingState.LIKE);
            dislikes = ratingRepository.findAllByEventIdAndState(eventId, RatingState.DISLIKE);
        }


        return RatingStateResult.builder()
                .event(eventId)
                .eventTitle(event.getTitle())
                .sumLikes(likes.size())
                .sumDislikes(dislikes.size())
                .rating(eventService.calculateRating(likes.size(),dislikes.size()))
                .likes(likes.stream().map(RatingMapper::mapToRatingShortDto).toList())
                .dislikes(dislikes.stream().map(RatingMapper::mapToRatingShortDto).toList())
                .build();
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkExistRating(Long userId, Long eventId) {

        if (ratingRepository.existsByUserIdAndEventId(userId, eventId)) {
            log.warn("Оценка от пользователя с id = {} на событие c id = {} уже существует", userId, eventId);
            throw new ConflictException("Оценка от пользователя с id = " + userId + " на событие c id = "
                    + eventId + " уже существует");
        }
    }

    private void checkRatingState(RatingState state) {

        if (!state.equals(RatingState.LIKE) && !state.equals(RatingState.DISLIKE)) {
            log.warn("Указана не существующая оценка на событие.");
            throw new ValidationException("Указана не существующая оценка на событие");
        }
    }

    private Rating findRating(Long userId, Long eventId) {
        return ratingRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Оценка не найдена"));
    }

    private User findAndCheckUser(Long userId) {
        return userService.findUserById(userId);
    }


    private Event findAndCheckEvent(Long eventId, Long userId) {

        Event event = eventService.findEventById(eventId);

        if ((event.getInitiator().getId()).equals(userId)) {
            log.warn("Инициатор события не может поставить оценку своёму событию");
            throw new ConflictException("Инициатор события не может поставить оценку своёму событию");
        }

        if (!event.getState().equals(PUBLISHED)) {
            log.warn("Нельзя поставить оценку неопубликованному событию.");
            throw new ConflictException("Нельзя поставить оценку неопубликованному событию.");
        }

        ParticipationRequest request = requestService.findParticipationRequestByUserIdAndEventId(userId, eventId);
        if (request == null || request.getStatus() != CONFIRMED) {
            log.warn("Оценку возможно поставить только если пользователь участвовал в событии.");
            throw new ConflictException("Оценку возможно поставить только если пользователь участвовал в событии.");
        }

        return event;
    }
}