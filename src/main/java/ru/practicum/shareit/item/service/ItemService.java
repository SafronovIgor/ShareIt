package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemResponseDto createItem(Long userId, ItemCreationRequestDto itemDto);

    ItemResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto);

    ItemResponseDto getItemById(Long itemId, Long userId);

    List<ItemResponseDto> getAllItemByIdOwner(Long userId);

    List<Item> searchAvailableItems(String textForSearchDto);
}
