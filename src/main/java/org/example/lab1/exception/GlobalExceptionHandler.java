package org.example.lab1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.example.lab1.featuretoggle.exception.FeatureNotAvailableException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Стандартизований метод для response body
    private Map<String, Object> createErrorResponse(HttpStatus status, String error, String message, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);
        return body;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // Структура відповіді для помилок валідації
        Map<String, Object> body = createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                "Validation failed for object 'ProductDTO'",
                request.getDescription(false)
        );
        body.put("errors", errors);

        // Логуємо помилку для більшої детальності
        logger.error("Validation error: {}", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        // Логуємо повідомлення про помилку
        logger.error("Unexpected error: ", ex);

        // Структура відповіді для загальних помилок
        Map<String, Object> body = createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred. Please contact support if the issue persists.",
                request.getDescription(false)
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CatNotFoundException.class)
    public ResponseEntity<String> handleCatNotFoundException(CatNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FeatureNotAvailableException.class)
    public ResponseEntity<Void> handleFeatureToggleException(FeatureNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
