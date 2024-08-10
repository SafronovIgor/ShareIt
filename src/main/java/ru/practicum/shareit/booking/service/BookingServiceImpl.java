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

        var item = itemStorage.findById(itemId).orElseThrow(() -> {
            log.warn("Error creating booking, item with id {} was not found.", itemId);
            return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
        });

        var user = userStorage.findById(userId).orElseThrow(() -> {
            log.warn("Error creating booking, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("User with the id '%s' does not exist", userId));
        });

        if (!itemStorage.existsByIdAndAvailableTrue(itemId)) {
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
