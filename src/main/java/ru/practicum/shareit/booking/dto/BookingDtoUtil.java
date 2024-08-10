package ru.practicum.shareit.booking.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class BookingDtoUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static Booking toBooking(BookingCreationRequestDto bookingCreationRequestDto, User user, Item item) {
        return Booking.builder()
                .start(bookingCreationRequestDto.getStart())
                .end(bookingCreationRequestDto.getEnd())
                .item(item)
                .booker(user)
                .status(Status.WAITING)
                .build();
    }

    public static BookingResponseDto toBookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .start(LocalDateTime.parse(formatDateTime(booking.getStart())))
                .end(LocalDateTime.parse(formatDateTime(booking.getEnd())))
                .item(new ItemResponseDto(booking.getItem().getId(), booking.getItem().getName()))
                .booker(new UserResponseDto(booking.getBooker().getId()))
                .status(booking.getStatus())
                .build();
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}