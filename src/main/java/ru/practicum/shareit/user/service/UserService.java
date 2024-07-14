package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.user.dto.UserUpdateRequestDto;

public interface UserService {
    User createUser(UserCreationRequestDto user);

    User updateUser(Long id, UserUpdateRequestDto user);

}
