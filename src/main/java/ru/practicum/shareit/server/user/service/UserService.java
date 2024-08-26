package ru.practicum.shareit.server.user.service;

import ru.practicum.shareit.server.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.server.user.dto.UserResponseDto;
import ru.practicum.shareit.server.user.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreationRequestDto userDto);

    UserResponseDto updateUserById(Long id, UserUpdateRequestDto userDto);

    UserResponseDto getUserById(Long id);

    void deleteUserById(Long id);

    List<UserResponseDto> getAllUsers();
}
