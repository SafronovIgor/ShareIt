package ru.practicum.shareit.bookings.service;

import ru.practicum.shareit.bookings.dto.BookingRequestDto;
import ru.practicum.shareit.bookings.dto.BookingResponseDto;
import ru.practicum.shareit.enums.State;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, Long userId);

    BookingResponseDto approveBooking(Long userId, Boolean approved, String bookingId);

    BookingResponseDto getBooking(String bookingId, Long userId);

    List<BookingResponseDto> getAllBooking(Long userId, State state);

}
