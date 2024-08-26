package ru.practicum.shareit.users.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.users.User;

@UtilityClass
public class UserMapper {
    public User toUser(UserCreationRequestDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
