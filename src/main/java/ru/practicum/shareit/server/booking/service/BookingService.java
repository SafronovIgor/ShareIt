package ru.practicum.shareit.server.booking.service;

import ru.practicum.shareit.server.booking.dto.BookingRequestDto;
import ru.practicum.shareit.server.booking.dto.BookingResponseDto;
import ru.practicum.shareit.server.booking.enums.State;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, Long userId);

    BookingResponseDto approveBooking(Long userId, String approved, String bookingId);

    BookingResponseDto getBooking(String bookingId, Long userId);

    List<BookingResponseDto> getAllBooking(Long userId, State state);

}
