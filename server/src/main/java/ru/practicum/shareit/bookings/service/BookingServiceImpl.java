package ru.practicum.shareit.bookings.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.bookings.Booking;
import ru.practicum.shareit.bookings.dto.BookingMapper;
import ru.practicum.shareit.bookings.dto.BookingRequestDto;
import ru.practicum.shareit.bookings.dto.BookingResponseDto;
import ru.practicum.shareit.bookings.repository.BookingRepository;
import ru.practicum.shareit.enums.State;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.exception.ItemNotAvailableException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.items.repository.ItemRepository;
import ru.practicum.shareit.items.service.ItemServiceImpl;
import ru.practicum.shareit.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final ItemServiceImpl itemService;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, Long userId) {
        log.info("Creating a booking for user with id {}", userId);
        var itemId = bookingRequestDto.getItemId();

        if (bookingRequestDto.getStart() == null) {
            bookingRequestDto.setStart(LocalDateTime.now());
        }

        var user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Error creating booking, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("Booking create failed by wrong userId: '%s'", userId));
        });

        var item = itemRepository.findById(itemId).orElseThrow(() -> {
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

        return BookingMapper.toBookingResponseDto(
                bookingRepository.save(BookingMapper.toNewBooking(bookingRequestDto, user, item))
        );
    }

    @Override
    public BookingResponseDto approveBooking(Long userId, Boolean approved, String bookingId) {
        log.info("Approving booking with id {} for user with id {}", bookingId, userId);
        if (approved) {

            var booking = bookingRepository.findById(Long.parseLong(bookingId)).orElseThrow(
                    () -> new ObjectNotFoundException("Booking not found. Please verify the bookingId and try again."));

            if (!userRepository.existsById(userId)) {
                throw new RuntimeException("User not found. Please verify your UserId and try again.");
            }

            if (itemService.isOwnerItem(booking.getItem(), userId)) {
                booking.setStatus(Status.APPROVED);
                return BookingMapper.toBookingResponseDto(booking);
            }
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDto getBooking(String bookingId, Long userId) {
        log.info("Getting booking with id {} for user with id {}", bookingId, userId);
        var booking = bookingRepository.findById(Long.parseLong(bookingId)).orElseThrow(
                () -> new ObjectNotFoundException("No booking found for the provided bookingId"));

        if (isOwnerBooking(booking, userId) || itemService.isOwnerItem(booking.getItem(), userId)) {
            return BookingMapper.toBookingResponseDto(booking);
        } else {
            throw new RuntimeException(
                    "Access denied. The user is neither the" +
                    " owner of the booking nor the owner of" +
                    " the item associated with the booking."
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getAllBooking(Long userId, State state) {
        log.info("Getting all bookings with state {} for user with id {}", state, userId);
        List<Booking> bookings = switch (state) {
            case ALL -> {
                if (!userRepository.existsById(userId)) throw new ObjectNotFoundException(
                        "User not found. Please verify your UserId and try again.");
                yield bookingRepository.findAllByUserId(userId);
            }
            case CURRENT -> bookingRepository.findCurrentBookings(userId);
            case PAST -> bookingRepository.findPastBookings(userId);
            case FUTURE -> bookingRepository.findFutureBookings(userId);
            case WAITING -> bookingRepository.findAllByStatusOrderByStartDesc(userId, Status.WAITING);
            case REJECTED -> bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
        };
        return BookingMapper.toListBookingResponseDto(bookings);
    }

    private boolean isOwnerBooking(Booking booking, Long userId) {
        return booking.getBooker().getId().equals(userId);
    }

}
