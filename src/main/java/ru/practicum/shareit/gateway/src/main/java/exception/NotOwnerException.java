package ru.practicum.shareit.gateway.src.main.java.exception;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException() {
        super("Only the owner can edit this item.");
    }
}