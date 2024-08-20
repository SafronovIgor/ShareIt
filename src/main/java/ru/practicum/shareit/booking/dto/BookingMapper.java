package ru.practicum.shareit.booking.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.Constants.FORMATTER;

@UtilityClass
public class BookingMapper {
    public static Booking toNewBooking(BookingRequestDto bookingRequestDto, User user, Item item) {
        return Booking.builder()
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .item(item)
                .booker(user)
                .status(Status.WAITING)
                .build();
    }

    public static BookingResponseDto toBookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .start(LocalDateTime.parse(formatDateTime(booking.getStart())))
                .end(LocalDateTime.parse(formatDateTime(booking.getEnd())))
                .item(new ItemResponseDto(booking.getItem().getId(), booking.getItem().getName()))
                .booker(new UserResponseDto(booking.getBooker().getId()))
                .status(booking.getStatus())
                .build();
    }

    public static List<BookingResponseDto> toListBookingResponseDto(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}