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
            throw new EmailAlreadyExistsException(userDto.getEmail());
        });
        return userStorage.save(UserCreationRequestDto.toUser(userDto));
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateRequestDto userDto) {
        return userStorage.findById(userId)
                .map(u -> {
                    if (userDto.getName() != null) {
                        u.setName(userDto.getName());
                    }
                    if (userDto.getEmail() != null) {
                        userStorage.findByEmail(userDto.getEmail())
                                .ifPresent(existingUser -> {
                                    if (existingUser.getId() != userId) {
                                        throw new EmailAlreadyExistsException(userDto.getEmail());
                                    }
                                });
                        u.setEmail(userDto.getEmail());
                    }
                    return userStorage.save(u);
                })
                .orElseThrow(() -> new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
    }

    @Override
    public User getUserById(Long id) {
        return userStorage.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
    }

    @Override
    public void deleteUserById(Long id) {
        userStorage.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.findAll();
    }

}
