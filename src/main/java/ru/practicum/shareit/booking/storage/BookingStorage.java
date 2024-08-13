package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.enums.Status;

import java.util.List;

public interface BookingStorage extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT
                b
            FROM
                Booking AS b
            WHERE
                b.booker.id = :id
            ORDER BY
                b.start DESC
            """)
    List<Booking> findAllByUserId(@Param("id") Long id);

    @Query("""
            SELECT
                b
            FROM
                Booking AS b
            WHERE
                b.booker.id = :id
                AND b.start <= CURRENT_TIMESTAMP
                AND b.end >= CURRENT_TIMESTAMP
            ORDER BY
                b.start DESC
            """)
    List<Booking> findCurrentBookings(@Param("id") Long id);

    @Query("""
            SELECT
                b
            FROM
                Booking AS b
            WHERE
                b.booker.id = :id
                AND b.end < CURRENT_TIMESTAMP
            ORDER BY
                b.start DESC
            """)
    List<Booking> findPastBookings(@Param("id") Long id);

    @Query("""
            SELECT
                b
            FROM
                Booking AS b
            WHERE
                b.booker.id = :id
                AND b.start > CURRENT_TIMESTAMP
            ORDER BY
                b.start DESC
            """)
    List<Booking> findFutureBookings(@Param("id") Long id);

    @Query("""
            SELECT
                b
            FROM
                Booking AS b
            WHERE
                b.booker.id = :id
                AND b.status = :status
            ORDER BY
                b.start DESC
            """)
    List<Booking> findAllByStatusOrderByStartDesc(@Param("id") Long id, @Param("status") Status status);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long id, Status status);
}