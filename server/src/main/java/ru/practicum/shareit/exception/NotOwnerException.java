package ru.practicum.shareit.exception;


public class NotOwnerException extends RuntimeException {
    public NotOwnerException() {
        super("Only the owner can edit this item.");
    }
}
