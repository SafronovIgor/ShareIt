package ru.practicum.shareit.requests;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.RequestDto;

import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestClient;

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(OWNER_USER_ID) Long userId) {
        log.info("Getting requests for user with id: {}", userId);
        return requestClient.getRequests(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                                @RequestBody @Valid RequestDto request) {
        log.info("Creating request for user with id: {}", userId);
        return requestClient.createRequest(userId, request);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequestsOtherUsers(@RequestHeader(OWNER_USER_ID) Long userId) {
        log.info("Getting all requests for all users excluding user with id: {}", userId);
        return requestClient.getAllRequestsOtherUsers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestsById(@RequestHeader(OWNER_USER_ID) Long userId,
                                                  @PathVariable String requestId) {
        log.info("Getting request with id: {} for user with id: {}", requestId, userId);
        return requestClient.getRequestsById(userId, requestId);
    }

}
