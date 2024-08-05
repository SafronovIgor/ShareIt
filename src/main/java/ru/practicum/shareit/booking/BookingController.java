package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    @PostMapping
    public Booking createBooking(BookingDto bookingDto) {
        return null;
    }
}
