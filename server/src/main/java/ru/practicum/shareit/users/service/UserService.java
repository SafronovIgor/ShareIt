package ru.practicum.shareit.users.service;

import ru.practicum.shareit.users.dto.UserCreationRequestDto;
import ru.practicum.shareit.users.dto.UserResponseDto;
import ru.practicum.shareit.users.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreationRequestDto userDto);

    UserResponseDto updateUserById(Long id, UserUpdateRequestDto userDto);

    UserResponseDto getUserById(Long id);

    void deleteUserById(Long id);

    List<UserResponseDto> getAllUsers();
}
