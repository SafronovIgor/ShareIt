package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.model.Item;

public interface ItemService {
    Item createItem(ItemCreationRequestDto itemDto);
}
