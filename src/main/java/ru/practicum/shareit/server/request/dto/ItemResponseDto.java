package ru.practicum.shareit.server.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.server.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponseDto {
    @NotBlank
    Long id;

    @NotBlank
    String description;

    @NotNull
    LocalDateTime created;

    List<Item> items;
}
