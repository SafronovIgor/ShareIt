package ru.practicum.shareit.server.booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemResponseDto {
    Long id;

    @NotBlank
    String name;

}
