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
        return requestClient.getRequests(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                                @RequestBody @Valid RequestDto request) {
        return requestClient.createRequest(userId, request);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequestsOtherUsers(@RequestHeader(OWNER_USER_ID) Long userId) {
        return requestClient.getAllRequestsOtherUsers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestsById(@RequestHeader(OWNER_USER_ID) Long userId,
                                                  @PathVariable String requestId) {
        return requestClient.getRequestsById(userId, requestId);
    }

}
