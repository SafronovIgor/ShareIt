package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.user.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    User createUser(UserCreationRequestDto user);

    User updateUser(Long id, UserUpdateRequestDto user);

    User getUserById(Long id);

    void deleteUserById(Long id);

    List<User> getAllUsers();
}
