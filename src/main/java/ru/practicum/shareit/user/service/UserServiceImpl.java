package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EmailAlreadyExistsException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserCreationRequestDto;
import ru.practicum.shareit.user.dto.UserUpdateRequestDto;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private static final String USER_NOT_FOUND_MSG = "User with the id '%s' does not exist";

    @Override
    @Transactional
    public User createUser(UserCreationRequestDto userDto) {
        userStorage.findByEmail(userDto.getEmail()).ifPresent(u -> {
            log.warn("Failed to create User '{}', email '{}' already exists.", userDto.getName(), userDto.getEmail());
            throw new EmailAlreadyExistsException(userDto.getEmail());
        });

        return Optional.of(userStorage.save(UserCreationRequestDto.toUser(userDto)))
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public User updateUserById(Long userId, UserUpdateRequestDto userDto) {
        return userStorage.findById(userId)
                .map(u -> {
                    if (userDto.getName() != null) {
                        u.setName(userDto.getName());
                    }
                    if (userDto.getEmail() != null) {
                        userStorage.findByEmail(userDto.getEmail())
                                .ifPresent(existingUser -> {
                                    if (existingUser.getId() != userId) {
                                        log.warn("Error updating user, email '{}' already exists.", userDto.getEmail());
                                        throw new EmailAlreadyExistsException(userDto.getEmail());
                                    }
                                });
                        u.setEmail(userDto.getEmail());
                    }
                    return userStorage.save(u);
                })
                .orElseThrow(() -> {
                    log.warn("Error updating user, user with id {} was not found.", userId);
                    return new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, userId));
                });
    }

    @Override
    public User getUserById(Long id) {
        return userStorage.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error getting user by id, user with id {} was not found.", id);
                    return new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, id));
                });
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (!userStorage.existsById(id)) {
            log.warn("Error deleting user by id, user with id {} was not found.", id);
            throw new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, id));
        }
        userStorage.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return Optional.of(userStorage.findAll())
                .orElseThrow(RuntimeException::new);
    }

}
