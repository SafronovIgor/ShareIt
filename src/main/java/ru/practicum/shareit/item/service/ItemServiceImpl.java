package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.model.Item;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Override
    public Item createItem(ItemCreationRequestDto itemDto) {
        return null;
    }
}
