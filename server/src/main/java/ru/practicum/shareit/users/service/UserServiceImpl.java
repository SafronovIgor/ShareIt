package ru.practicum.shareit.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EmailAlreadyExistsException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.users.dto.UserCreationRequestDto;
import ru.practicum.shareit.users.dto.UserMapper;
import ru.practicum.shareit.users.dto.UserResponseDto;
import ru.practicum.shareit.users.dto.UserUpdateRequestDto;
import ru.practicum.shareit.users.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.practicum.shareit.Constants.USER_NOT_FOUND_MSG;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserCreationRequestDto userDto) {
        log.info("Creating a new user with email {}", userDto.getEmail());
        userRepository.findByEmail(userDto.getEmail()).ifPresent(u -> {
            log.warn("Failed to create User '{}', email '{}' already exists.", userDto.getName(), userDto.getEmail());
            throw new EmailAlreadyExistsException(userDto.getEmail());
        });
        var user = Optional.of(userRepository.save(UserMapper.toUser(userDto)))
                .orElseThrow(RuntimeException::new);
        return UserMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUserById(Long userId, UserUpdateRequestDto userDto) {
        log.info("Updating user with id {}", userId);
        var user = userRepository.findById(userId)
                .map(u -> {
                    if (userDto.getName() != null && !userDto.getName().isBlank()) {
                        u.setName(userDto.getName());
                    }
                    if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
                        userRepository.findByEmail(userDto.getEmail())
                                .ifPresent(existingUser -> {
                                    if (!Objects.equals(existingUser.getId(), userId)) {
                                        log.warn("Error updating user, email '{}' already exists.", userDto.getEmail());
                                        throw new EmailAlreadyExistsException(userDto.getEmail());
                                    }
                                });
                        u.setEmail(userDto.getEmail());
                    }
                    return u;
                })
                .orElseThrow(() -> {
                    log.warn("Error updating user, user with id {} was not found.", userId);
                    return new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, userId));
                });
        return UserMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        log.info("Getting user with id {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error getting user by id, user with id {} was not found.", id);
                    return new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, id));
                });
        return UserMapper.toUserResponseDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Deleting user with id {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Error deleting user by id, user with id {} was not found.", id);
            throw new ObjectNotFoundException(String.format(USER_NOT_FOUND_MSG, id));
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Getting all users");
        return Optional.of(userRepository.findAll())
                .orElseThrow(RuntimeException::new)
                .stream()
                .map(UserMapper::toUserResponseDto)
                .toList();
    }

}
