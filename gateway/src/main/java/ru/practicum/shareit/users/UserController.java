package ru.practicum.shareit.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.users.dto.UserRequestDto;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequestDto userDto) {
        log.info("Received request to create a new user : {}", userDto.toString());
        return userClient.createUser(userDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUserById(@PathVariable Long userId, @RequestBody UserRequestDto userDto) {
        log.info("Received request to update user by id {}, dto: {}", userId, userDto.toString());
        return userClient.updateUserById(userId, userDto);
    }
}
