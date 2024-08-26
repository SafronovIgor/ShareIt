package ru.practicum.shareit.server.item.service;

import ru.practicum.shareit.server.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.server.item.dto.ItemResponseDto;
import ru.practicum.shareit.server.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.server.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemResponseDto createItem(Long userId, ItemCreationRequestDto itemDto, Long requestId);

    ItemResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto);

    ItemResponseDto getItemById(Long itemId, Long userId);

    List<ItemResponseDto> getAllItemByIdOwner(Long userId);

    List<Item> searchAvailableItems(String textForSearchDto);
}
