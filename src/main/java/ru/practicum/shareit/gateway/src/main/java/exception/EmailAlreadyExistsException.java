package ru.practicum.shareit.gateway.src.main.java.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email '" + email + "' already exists");
    }

}
