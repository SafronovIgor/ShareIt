package ru.practicum.shareit.bookings.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.items.dto.ItemResponseDto;
import ru.practicum.shareit.users.dto.UserResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponseDto {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    ItemResponseDto item;
    UserResponseDto booker;
    Status status;
}
