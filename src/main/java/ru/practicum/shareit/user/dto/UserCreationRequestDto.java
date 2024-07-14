package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.User;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class UserCreationRequestDto extends UserDto {
    @NotBlank
    String name;

    @NotNull
    @Email
    String email;

    public static User toUser(UserDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

