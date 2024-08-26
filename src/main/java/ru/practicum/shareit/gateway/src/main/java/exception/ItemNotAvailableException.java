package ru.practicum.shareit.gateway.src.main.java.exception;

public class ItemNotAvailableException extends RuntimeException {
    public ItemNotAvailableException(String format) {
        super(format);
    }
}
