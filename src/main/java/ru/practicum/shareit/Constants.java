package ru.practicum.shareit;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constants {
    public static final String OWNER_USER_ID = "X-Sharer-User-Id";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final String USER_NOT_FOUND_MSG = "User with the id '%s' does not exist";

}
