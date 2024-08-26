package ru.practicum.shareit.gateway.src.main.java.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.server.request.dto.ItemRequestDto;
import ru.practicum.shareit.server.request.dto.ItemResponseDto;
import ru.practicum.shareit.server.request.service.ItemRequestService;

import java.util.List;

import static ru.practicum.shareit.gateway.src.main.java.Constants.OWNER_USER_ID;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService requestService;

    @PostMapping
    public ItemResponseDto createItemRequest(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                             @RequestBody ItemRequestDto request) {
        log.info("Received request to create a new item request for user with id {}", userId);
        return requestService.createItemRequest(request, userId);
    }

    @GetMapping
    public List<ItemResponseDto> getItemRequests(@RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get all item requests for user with id {} ", userId);
        return requestService.getItemRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemResponseDto> getAllItemRequestsOtherUsers(@RequestHeader(value = OWNER_USER_ID) Long userId) {
        log.info("Received request to get all item requests for other users");
        return requestService.getAllItemRequestsOtherUsers(userId);
    }

    @GetMapping("/{requestId}")
    public ItemResponseDto getItemRequestsById(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                               @PathVariable String requestId) {
        log.info("Received request to get information about item request with id {} for user with id {}",
                requestId, userId);
        return requestService.getItemRequestsById(userId, Long.parseLong(requestId));
    }

}
