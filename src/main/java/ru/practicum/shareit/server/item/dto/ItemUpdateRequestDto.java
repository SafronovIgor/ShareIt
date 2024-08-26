package ru.practicum.shareit.server.item.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateRequestDto {
    Long id;

    @Size(max = 255)
    String name;

    String description;

    Boolean available;

}
