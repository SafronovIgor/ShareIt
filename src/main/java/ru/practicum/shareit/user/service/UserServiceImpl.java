package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailAlreadyExistsException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User createUser(UserDto user) {
        userStorage.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new EmailAlreadyExistsException(user.getEmail());
        });

        return userStorage.save(UserDto.toUser(user));
    }
}
