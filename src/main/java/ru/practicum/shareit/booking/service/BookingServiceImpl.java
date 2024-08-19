package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDtoUtil;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.enums.State;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.exception.ItemNotAvailableException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final ItemServiceImpl itemService;
    private final BookingStorage bookingStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, Long userId) {
        log.info("Creating a booking for user with id {}", userId);
        var itemId = bookingRequestDto.getItemId();

        if (bookingRequestDto.getStart() == null) {
            bookingRequestDto.setStart(LocalDateTime.now());
        }

        var user = userStorage.findById(userId).orElseThrow(() -> {
            log.error("Error creating booking, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("Booking create failed by wrong userId: '%s'", userId));
        });

        var item = itemStorage.findById(itemId).orElseThrow(() -> {
            log.warn("Error creating booking, item with id {} was not found.", itemId);
            return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
        });

        if (!bookingRequestDto.getStart().isBefore(bookingRequestDto.getEnd())) {
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
                bookingStorage.save(BookingDtoUtil.toNewBooking(bookingRequestDto, user, item))
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingResponseDto approveBooking(Long userId, String approved, String bookingId) {
        log.info("Approving booking with id {} for user with id {}", bookingId, userId);
        if (Boolean.parseBoolean(approved)) {

            var optionalBooking = bookingStorage.findById(Long.parseLong(bookingId));
            var booking = optionalBooking.orElseThrow(
                    () -> new ObjectNotFoundException("Booking not found. Please verify the bookingId and try again."));

            if (!userStorage.existsById(userId)) {
                throw new RuntimeException("User not found. Please verify your UserId and try again.");
            }

            if (itemService.isOwnerItem(booking.getItem(), userId)) {
                booking.setStatus(Status.APPROVED);
                bookingStorage.save(booking);

                return BookingDtoUtil.toBookingResponseDto(booking);
            }
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public BookingResponseDto getBooking(String bookingId, Long userId) {
        log.info("Getting booking with id {} for user with id {}", bookingId, userId);
        var booking = bookingStorage.findById(Long.parseLong(bookingId)).orElseThrow(
                () -> new ObjectNotFoundException("No booking found for the provided bookingId"));

        if (isOwnerBooking(booking, userId) || itemService.isOwnerItem(booking.getItem(), userId)) {
            return BookingDtoUtil.toBookingResponseDto(booking);
        } else {
            throw new RuntimeException(
                    "Access denied. The user is neither the" +
                    " owner of the booking nor the owner of" +
                    " the item associated with the booking."
            );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<BookingResponseDto> getAllBooking(Long userId, State state) {
        log.info("Getting all bookings with state {} for user with id {}", state, userId);
        List<Booking> bookings = switch (state) {
            case ALL -> bookingStorage.findAllByUserId(userId);
            case CURRENT -> bookingStorage.findCurrentBookings(userId);
            case PAST -> bookingStorage.findPastBookings(userId);
            case FUTURE -> bookingStorage.findFutureBookings(userId);
            case WAITING -> bookingStorage.findAllByStatusOrderByStartDesc(userId, Status.WAITING);
            case REJECTED -> bookingStorage.findByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
        };
        return BookingDtoUtil.toListBookingResponseDto(bookings);
    }

    private boolean isOwnerBooking(Booking booking, Long userId) {
        return booking.getBooker().getId().equals(userId);
    }

}
