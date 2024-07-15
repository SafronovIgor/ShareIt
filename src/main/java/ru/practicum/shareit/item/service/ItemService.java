package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemCreationResponseDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateResponseDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemCreationResponseDto createItem(Long userId, ItemCreationRequestDto itemDto);

    ItemUpdateResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto);

    ItemUpdateResponseDto getItemById(Long itemId);

    List<ItemUpdateResponseDto> getAllItemByIdOwner(Long userId);

    List<Item> searchAvailableItems(String textForSearchDto);
}
