package ru.practicum.shareit.bookings.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JsonTest
class BookItemRequestDtoTest {
    private Validator validator;
    private BookItemRequestDto dto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateFutureDate() {
        dto = new BookItemRequestDto(1L,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Start date should be in the future or present");
    }

    @Test
    void validateFutureOrPresentDate() {
        dto = new BookItemRequestDto(1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().minusDays(1));
        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "End date should be in the future");
    }

    @Test
    void validateCorrectDates() {
        dto = new BookItemRequestDto(1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2));
        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Should not have violations");
    }
}