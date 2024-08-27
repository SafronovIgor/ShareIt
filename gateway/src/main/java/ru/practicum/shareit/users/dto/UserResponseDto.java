package ru.practicum.shareit.users.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    Long id;
    String name;
    String email;
}
