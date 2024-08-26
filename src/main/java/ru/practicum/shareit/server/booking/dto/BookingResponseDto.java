package ru.practicum.shareit.server.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.server.booking.enums.Status;

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
