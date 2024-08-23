package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;

import java.util.List;

import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @PostMapping
    public ItemResponseDto createItemRequest(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                             @RequestBody ItemRequestDto request) {
        log.info("Received request to create a new item request for user with id {}", userId);
        return null;
    }

    @GetMapping
    public List<ItemResponseDto> getItemRequests(@RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get all item requests for user with id {} ", userId);
        return null;
    }

    @GetMapping("/all")
    public List<ItemResponseDto> getAllItemRequestsOtherUsers(@RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get all item requests for other users");
        return null;
    }

    @GetMapping("/{requestId}")
    public ItemResponseDto getItemRequestsById(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                               @PathVariable String requestId) {
        log.info("Received request to get information about item request with id {} for user with id {}",
                requestId, userId);
        return null;
    }

}
