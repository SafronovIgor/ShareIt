package ru.practicum.shareit.requests.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.items.Item;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponseDto {
    Long id;
    String description;
    LocalDateTime created;
    List<Item> items;
}
