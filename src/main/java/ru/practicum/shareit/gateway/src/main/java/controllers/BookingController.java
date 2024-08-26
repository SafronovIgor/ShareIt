package ru.practicum.shareit.gateway.src.main.java.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.booking.dto.BookingRequestDto;
import ru.practicum.shareit.server.booking.dto.BookingResponseDto;
import ru.practicum.shareit.server.booking.enums.State;
import ru.practicum.shareit.server.booking.service.BookingService;

import java.util.List;

import static ru.practicum.shareit.gateway.src.main.java.Constants.OWNER_USER_ID;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto createBooking(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                            @RequestBody @Valid BookingRequestDto bookingRequestDto) {
        log.info("Received request to create a new booking for user with id {}", userId);
        return bookingService.createBooking(bookingRequestDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approveBooking(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                             @RequestParam String approved,
                                             @PathVariable String bookingId) {
        log.info("Received request to approve booking with id {} for user with id {}", bookingId, userId);
        return bookingService.approveBooking(userId, approved, bookingId);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBooking(@PathVariable String bookingId,
                                         @RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get information about booking with id {} for user with id {}", bookingId, userId);
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingResponseDto> getAllBooking(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                                  @RequestParam(value = "state", defaultValue = "ALL") State state) {
        log.info("Received request to get all bookings for user with id {} with state {}", userId, state);
        return bookingService.getAllBooking(userId, state);
    }
}
