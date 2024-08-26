package ru.practicum.shareit.bookings;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.items.Item;
import ru.practicum.shareit.users.User;

import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

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

    @Temporal(TIMESTAMP)
    @Column(name = "start_time_booking")
    LocalDateTime start;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time_booking")
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
