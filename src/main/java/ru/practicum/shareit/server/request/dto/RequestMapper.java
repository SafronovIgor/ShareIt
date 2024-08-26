package ru.practicum.shareit.server.request.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.request.ItemRequest;
import ru.practicum.shareit.server.user.User;

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
