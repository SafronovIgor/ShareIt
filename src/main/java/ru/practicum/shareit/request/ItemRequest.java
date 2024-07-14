package ru.practicum.shareit.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {
    long id;
    String description;
    User requestor;
    LocalDateTime created;
}
