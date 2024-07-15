package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreationRequestDto userDto);

    UserResponseDto updateUserById(Long id, UserUpdateRequestDto userDto);

    UserResponseDto getUserById(Long id);

    void deleteUserById(Long id);

    List<UserResponseDto> getAllUsers();
}
