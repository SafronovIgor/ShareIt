package ru.practicum.shareit.items.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDto {
    @NotBlank
    String name;
    @NotBlank
    String description;
    Boolean available;
    @NotNull
    Long ownerId;
    Long requestId;
}
