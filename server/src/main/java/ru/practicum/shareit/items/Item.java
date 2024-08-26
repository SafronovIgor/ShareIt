package ru.practicum.shareit.items;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.users.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String description;

    Boolean available;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    User owner;

    @ManyToOne
    @JoinColumn(name = "request_item_request_id")
    ItemRequest request;
}
