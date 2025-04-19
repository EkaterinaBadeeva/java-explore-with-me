package ru.practicum.explore_with_me.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.exceptions.ConflictException;
import ru.practicum.explore_with_me.exceptions.NotFoundException;
import ru.practicum.explore_with_me.exceptions.ValidationException;
import ru.practicum.explore_with_me.user.dao.UserRepository;
import ru.practicum.explore_with_me.user.dto.UserDto;
import ru.practicum.explore_with_me.user.mapper.UserMapper;
import ru.practicum.explore_with_me.user.model.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        log.info("Получение информации о пользователях.");
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage;

        if (ids != null && !ids.isEmpty()) {
            userPage = userRepository.findUsersByIdIn(ids, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        return userPage.getContent()
                .stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        log.info("Добавление пользователя.");
        User user = UserMapper.mapToUser(userDto);
        checkConditions(user);
        checkEmail(user);
        user = userRepository.save(user);

        return UserMapper.mapToUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        log.info("Удаление пользователя.");

        checkId(id);

        userRepository.deleteById(id);
    }

    @Override
    public User findUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkEmail(User user) {
        for (User us : userRepository.findAll()) {
            if (us.getEmail().equals(user.getEmail())) {
                log.warn("Пользователь уже существует.");
                throw new ConflictException("Пользователь с email = " + user.getEmail() + " уже существует");
            }
        }
    }

    private void checkConditions(User user) {

        if (user.getName().isEmpty()) {
            log.warn("Задано пустое имя пользователя.");
            throw new ValidationException("Задано пустое имя пользователя");
        }

        if (user.getEmail().isEmpty()) {
            log.warn("Задан пустой email пользователя.");
            throw new ValidationException("Задан пустой email пользователя");
        }
    }
}
