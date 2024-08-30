package ru.practicum.shareit.items.service;

import ru.practicum.shareit.items.Item;
import ru.practicum.shareit.items.dto.ItemCreationRequestDto;
import ru.practicum.shareit.items.dto.ItemResponseDto;
import ru.practicum.shareit.items.dto.ItemUpdateRequestDto;

import java.util.List;

public interface ItemService {
    ItemResponseDto createItem(Long userId, ItemCreationRequestDto itemDto);

    ItemResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto);

    ItemResponseDto getItemById(Long itemId, Long userId);

    List<ItemResponseDto> getAllItemByIdOwner(Long userId);

    List<Item> searchAvailableItems(String textForSearchDto);
}
