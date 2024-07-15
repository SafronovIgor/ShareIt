package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemCreationRequestDto {
    @NotBlank
    @Size(max = 255)
    String name;
    @NotBlank
    @Size(max = 255)
    String description;
    @NotNull
    Boolean available;
    Long ownerId;
}
