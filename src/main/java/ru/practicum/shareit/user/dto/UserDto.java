package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.User;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @NotBlank
    String name;
    @Email
    String email;

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
