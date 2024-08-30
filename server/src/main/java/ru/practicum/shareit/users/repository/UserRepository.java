package ru.practicum.shareit.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.users.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}