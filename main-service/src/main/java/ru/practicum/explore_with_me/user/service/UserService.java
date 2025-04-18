package ru.practicum.explore_with_me.user.service;

import ru.practicum.explore_with_me.user.dto.UserDto;
import ru.practicum.explore_with_me.user.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto create(UserDto userDto);

    void deleteUserById(Long id);

    User findUserById(Long userId);
}
