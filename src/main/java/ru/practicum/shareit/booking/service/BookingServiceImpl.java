package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreationRequestDto;
import ru.practicum.shareit.booking.dto.BookingDtoUtil;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.ItemNotAvailableException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingStorage bookingStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public BookingResponseDto createBooking(BookingCreationRequestDto bookingCreationRequestDto, Long userId) {
        var itemId = bookingCreationRequestDto.getItemId();

        if (bookingCreationRequestDto.getStart() == null) {
            bookingCreationRequestDto.setStart(LocalDateTime.now());
        }

        var user = userStorage.findById(userId).orElseThrow(() -> {
            log.warn("Error creating booking, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("Booking create failed by wrong userId: '%s'", userId));
        });

        var item = itemStorage.findById(itemId).orElseThrow(() -> {
            log.warn("Error creating booking, item with id {} was not found.", itemId);
            return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
        });

        if (!bookingCreationRequestDto.getStart().isBefore(bookingCreationRequestDto.getEnd())) {
            log.warn("Booking create failed by end in past or end time is not given.");
            throw new IllegalArgumentException("Booking create failed by end in past or end time is not given.");
        }

        if (!item.getAvailable()) {
            log.warn("Error creating booking, item with the id {} is not available", itemId);
            throw new ItemNotAvailableException(String.format(
                    "Item with the id '%s' is not available", itemId
            ));
        }

        return BookingDtoUtil.toBookingResponseDto(
                bookingStorage.save(BookingDtoUtil.toBooking(bookingCreationRequestDto, user, item))
        );
    }
}
