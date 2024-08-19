package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time_booking", nullable = false)
    LocalDateTime start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time_booking", nullable = false)
    LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne
    @JoinColumn(name = "booker_user_id")
    User booker;

    @Enumerated(EnumType.ORDINAL)
    Status status;
}
