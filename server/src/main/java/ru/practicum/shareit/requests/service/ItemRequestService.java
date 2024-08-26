package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemResponseDto;

import java.util.List;

public interface ItemRequestService {
    ItemResponseDto createItemRequest(ItemRequestDto itemRequestDto, Long userId);

    List<ItemResponseDto> getItemRequests(Long userId);

    List<ItemResponseDto> getAllItemRequestsOtherUsers(Long userId);

    ItemResponseDto getItemRequestsById(Long userId, Long requestId);

}
