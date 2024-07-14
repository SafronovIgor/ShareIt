package ru.practicum.shareit.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<HttpError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("ValidationException: {}", e.getMessage());
        var errorMessage = extractErrorMessage(e);
        var httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(errorMessage)
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler
    public ResponseEntity<HttpError> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.warn("EmailAlreadyExistsException: {}", e.getMessage());
        var httpStatus = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(e.getMessage())
                        .build(),
                httpStatus
        );
    }

    private String extractErrorMessage(MethodArgumentNotValidException e) {
        var fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            var fieldName = fieldError.getField();
            var defaultMessage = fieldError.getDefaultMessage();

            return String.format("Validation failed for '%s': %s", fieldName, defaultMessage);
        }

        return "Unknown error.";
    }

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class HttpError {
        int statusCode;
        String message;
    }
}
