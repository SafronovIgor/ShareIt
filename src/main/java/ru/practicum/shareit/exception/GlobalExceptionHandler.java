package ru.practicum.shareit.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<HttpError> catchMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("ValidationException: {}", e.getMessage(), e);
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
    public ResponseEntity<HttpError> catchEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        log.warn("EmailAlreadyExistsException: {}", e.getMessage(), e);
        var httpStatus = HttpStatus.CONFLICT;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(e.getMessage())
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler
    public ResponseEntity<HttpError> catchObjectNotFoundException(ObjectNotFoundException e) {
        log.warn(e.getMessage(), e);
        var httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(e.getMessage())
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler
    public ResponseEntity<HttpError> catchRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message("An unexpected error occurred on the server. Please try again later.")
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler
    public ResponseEntity<HttpError> catchMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.warn("MissingRequestHeaderException: {}", e.getMessage(), e);
        var httpStatus = HttpStatus.BAD_REQUEST;

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

    @ExceptionHandler
    public ResponseEntity<HttpError> catchNotOwnerException(NotOwnerException e) {
        log.warn("NotOwnerException: {}", e.getMessage(), e);
        var httpStatus = HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(e.getMessage())
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler
    private ResponseEntity<HttpError> itemNotAvailableExceptionErrorMessage(ItemNotAvailableException e) {
        log.warn("ItemNotAvailableException: {}", e.getMessage(), e);
        var httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(e.getMessage())
                        .build(),
                httpStatus
        );
    }

    @ExceptionHandler
    public ResponseEntity<HttpError> catchMissingRequestHeaderException(BookingTimeException e) {
        log.warn("BookingTimeException: {}", e.getMessage(), e);
        var httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                HttpError.builder()
                        .statusCode(httpStatus.value())
                        .message(e.getMessage())
                        .build(),
                httpStatus
        );
    }

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class HttpError {
        int statusCode;
        String message;
    }
}
