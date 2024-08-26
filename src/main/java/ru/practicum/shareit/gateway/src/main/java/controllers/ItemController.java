package ru.practicum.shareit.gateway.src.main.java.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.server.item.dto.ItemResponseDto;
import ru.practicum.shareit.server.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.server.item.service.ItemService;

import java.util.List;

import static ru.practicum.shareit.gateway.src.main.java.Constants.OWNER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemResponseDto createItem(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                      @RequestBody @Valid ItemCreationRequestDto itemDto,
                                      @RequestParam(required = false) Long requestId) {
        log.info("Received request to create a new item, owner is {}", userId);
        return itemService.createItem(userId, itemDto, requestId);
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto updateItemById(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                          @PathVariable Long itemId,
                                          @RequestBody @Valid ItemUpdateRequestDto itemDto) {
        log.info("Received request to update item with id {}, owner is user id {}", itemId, userId);
        return itemService.updateItemById(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto getItemById(@PathVariable Long itemId,
                                       @RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get item with id {}", itemId);
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemResponseDto> getAllItemByIdOwner(@RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get all items owned by user with id {}", userId);
        return itemService.getAllItemByIdOwner(userId);
    }
}
