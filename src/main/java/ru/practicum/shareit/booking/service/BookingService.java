package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreationRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

public interface BookingService {

    BookingResponseDto createBooking(BookingCreationRequestDto bookingCreationRequestDto, Long userId);
}
