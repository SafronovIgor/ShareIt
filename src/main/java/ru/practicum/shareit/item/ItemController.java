package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                           @RequestBody @Valid ItemCreationRequestDto itemDto) {
        log.info("Received request to create a new item, owner is {}", userId);
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItemById(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
                               @PathVariable @Positive @Min(1L) Long itemId,
                               @RequestBody @Valid ItemUpdateRequestDto itemDto) {
        log.info("Received request to update item with id {}, owner is user id {}", itemId, userId);
        return itemService.updateItemById(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable @Positive @Min(1L) Long itemId) {
        log.info("Received request to get item with id {}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<Item> getAllItemByIdOwner(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        log.info("Received request to get all items owned by user with id {}", userId);
        return itemService.getAllItemByIdOwner(userId);
    }
}
