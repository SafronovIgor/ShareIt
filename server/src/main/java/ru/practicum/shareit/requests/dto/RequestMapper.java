package ru.practicum.shareit.requests.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.items.Item;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.users.User;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class RequestMapper {
    public ItemRequest toItemRequest(ItemRequestDto requestDto, User requestor) {
        return ItemRequest.builder()
                .description(requestDto.getDescription())
                .requestor(requestor)
                .created(LocalDateTime.now())
                .build();
    }

    public ItemResponseDto toItemResponseDto(ItemRequest request) {
        return ItemResponseDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .build();
    }

    public ItemResponseDto toItemResponseDto(ItemRequest request, List<Item> items) {
        return ItemResponseDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(items)
                .build();
    }
}
