package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                           @RequestBody @Valid ItemCreationRequestDto itemDto) {
        log.info("Получен запрос на создание вещи, владелец {}", userId);
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItemById(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                               @PathVariable Long itemId,
                               @RequestBody @Valid ItemUpdateRequestDto itemDto) {
        log.info("Получен запрос на обновление вещи по id {}, владелец вещи id {}", itemId, userId);
        return itemService.updateItemById(userId, itemId, itemDto);
    }

}
