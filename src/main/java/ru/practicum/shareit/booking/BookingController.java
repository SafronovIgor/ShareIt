package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.enums.State;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final String ownerUserId = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto createBooking(@RequestHeader(value = ownerUserId) Long userId,
                                            @RequestBody @Valid BookingRequestDto bookingRequestDto) {
        log.info("Received request to create a new booking");
        return bookingService.createBooking(bookingRequestDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponseDto approveBooking(
            @RequestHeader(value = ownerUserId) Long userId,
            @RequestParam String approved,
            @PathVariable String bookingId) {
        return bookingService.approveBooking(userId, approved, bookingId);
    }

    @GetMapping("/{bookingId}")
    public BookingResponseDto getBooking(
            @PathVariable String bookingId,
            @RequestHeader(value = ownerUserId) Long userId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingResponseDto> getAllBooking(
            @RequestHeader(value = ownerUserId) Long userId,
            @RequestParam(value = "state", required = false, defaultValue = "ALL") State state) {
        return bookingService.getAllBooking(userId, state);
    }
}
