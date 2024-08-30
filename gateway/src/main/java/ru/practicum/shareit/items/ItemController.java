package ru.practicum.shareit.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.items.dto.ItemRequestDto;

import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getAllItemByIdUser(@RequestHeader(OWNER_USER_ID) Long userId) {
        log.info("Getting all items for user with id: {}", userId);
        return itemClient.getAllItemByIdUser(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(OWNER_USER_ID) Long userId,
                                             @RequestBody @Valid ItemRequestDto itemDto) {
        log.info("Creating item for user with id: {}", userId);
        return itemClient.createItem(userId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable Long itemId, @RequestHeader(OWNER_USER_ID) Long userId) {
        log.info("Getting item with id: {} for user with id: {}", itemId, userId);
        return itemClient.getItemById(itemId, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItemById(@RequestHeader(OWNER_USER_ID) Long userId, @PathVariable Long itemId,
                                                 @RequestBody ItemRequestDto itemDto) {
        log.info("Updating item with id: {} for user with id: {}", itemId, userId);
        return itemClient.updateItemById(userId, itemId, itemDto);
    }
}
