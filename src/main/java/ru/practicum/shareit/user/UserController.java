package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.user.dto.UserUpdateRequestDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserCreationRequestDto userDto) {
        log.info("Received request to create a new user.");
        return userService.createUser(userDto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Received request to get all users.");
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable @Positive @Min(1L) Long userId) {
        log.info("Received request to get user by id {}", userId);
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserById(@PathVariable @Positive @Min(1L) Long userId,
                               @RequestBody @Valid UserUpdateRequestDto userDto) {
        log.info("Received request to update user by id {}", userId);
        return userService.updateUserById(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable @Positive @Min(1L) Long userId) {
        log.info("Received request to delete user by id {}", userId);
        userService.deleteUserById(userId);
    }
}
