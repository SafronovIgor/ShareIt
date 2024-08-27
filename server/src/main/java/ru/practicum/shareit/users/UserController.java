package ru.practicum.shareit.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.users.dto.UserCreationRequestDto;
import ru.practicum.shareit.users.dto.UserResponseDto;
import ru.practicum.shareit.users.dto.UserUpdateRequestDto;
import ru.practicum.shareit.users.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserCreationRequestDto userDto) {
        log.info("Received request to create a new user.");
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        log.info("Received request to get all users.");
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable Long userId) {
        log.info("Received request to get user by id {}", userId);
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponseDto updateUserById(@PathVariable Long userId,
                                          @RequestBody UserUpdateRequestDto userDto) {
        log.info("Received request to update user by id {}", userId);
        return userService.updateUserById(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) {
        log.info("Received request to delete user by id {}", userId);
        userService.deleteUserById(userId);
    }
}
