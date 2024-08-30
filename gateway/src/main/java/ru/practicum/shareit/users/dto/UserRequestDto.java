package ru.practicum.shareit.users.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    //тут есть моменты когда на запросох приходит null name or email =(
    String name;
    @Email
    String email;
}
