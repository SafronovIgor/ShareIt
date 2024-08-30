package ru.practicum.shareit.requests.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    //Тут тоже может быть null по тестам postgres
    String name;
    @NotBlank
    String description;
    Boolean available;
}
