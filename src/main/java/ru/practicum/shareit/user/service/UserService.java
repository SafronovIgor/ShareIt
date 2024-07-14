package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.user.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    User createUser(UserCreationRequestDto userDto);

    User updateUserById(Long id, UserUpdateRequestDto userDto);

    User getUserById(Long id);

    void deleteUserById(Long id);

    List<User> getAllUsers();
}
