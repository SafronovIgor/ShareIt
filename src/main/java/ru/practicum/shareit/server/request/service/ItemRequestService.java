package ru.practicum.shareit.server.request.service;

import ru.practicum.shareit.server.request.dto.ItemRequestDto;
import ru.practicum.shareit.server.request.dto.ItemResponseDto;

import java.util.List;

public interface ItemRequestService {
    ItemResponseDto createItemRequest(ItemRequestDto itemRequestDto, Long userId);

    List<ItemResponseDto> getItemRequests(Long userId);

    List<ItemResponseDto> getAllItemRequestsOtherUsers(Long userId);

    ItemResponseDto getItemRequestsById(Long userId, Long requestId);

}
