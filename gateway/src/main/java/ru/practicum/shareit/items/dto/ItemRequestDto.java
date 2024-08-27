package ru.practicum.shareit.items.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDto {
    String name;
    @NotBlank
    String description;
    Boolean available;
    Long ownerId;
    Long requestId;
}
