package ru.practicum.shareit.items.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemCreationRequestDto {
    String name;
    String description;
    Boolean available;
    Long ownerId;
    Long requestId;
}
