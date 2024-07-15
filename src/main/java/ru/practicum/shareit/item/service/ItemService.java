package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item createItem(Long userId, ItemCreationRequestDto itemDto);

    Item updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto);

    Item getItemById(Long itemId);

    List<Item> getAllItemByIdOwner(Long userId);
}
