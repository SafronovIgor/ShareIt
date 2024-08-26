package ru.practicum.shareit.items.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.bookings.Booking;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponseDto {
    Long id;
    String name;
    String description;
    Boolean available;
    Long ownerId;
    Booking lastBooking;
    Booking nextBooking;
    List<String> comments;
}
