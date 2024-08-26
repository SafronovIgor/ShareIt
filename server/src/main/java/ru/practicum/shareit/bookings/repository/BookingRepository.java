package ru.practicum.shareit.bookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.bookings.Booking;
import ru.practicum.shareit.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

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

    boolean existsByItemIdAndBookerId(Long itemId, Long userId);

    @Query("""
            SELECT
                b
            FROM
                Booking AS b
            WHERE
                b.booker.id = :userId
                AND b.item.id = :itemId
            ORDER BY
                b.end DESC
            """)
    List<Booking> findByItemIdAndBookerIdOrderByEndTimeDesc(@Param("itemId") Long itemId, @Param("userId") Long userId);

    @Query(value = "SELECT b FROM Booking b WHERE b.item.id = :itemId AND b.booker.id = :userId ORDER BY b.start DESC")
    Booking findLastBooking(@Param("itemId") Long itemId, @Param("userId") Long userId);

    @Query(value = "SELECT b FROM Booking b WHERE b.item.id = :itemId AND b.booker.id = :userId AND b.start > CURRENT_DATE ORDER BY b.start ASC")
    Booking findNextBooking(@Param("itemId") Long itemId, @Param("userId") Long userId);

    List<Booking> findAllByBookerIdAndItemIdAndStatusAndEndBefore(Long userId, Long itemId, Status status, LocalDateTime localDateTime);

}
