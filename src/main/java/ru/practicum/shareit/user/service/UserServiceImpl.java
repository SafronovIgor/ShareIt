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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

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
                .orElseThrow(() -> new ObjectNotFoundException("User with the given id does not exist"));
    }

}
