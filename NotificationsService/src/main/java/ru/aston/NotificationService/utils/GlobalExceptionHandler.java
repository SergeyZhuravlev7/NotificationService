package ru.aston.NotificationService.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.kafka.KafkaException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.aston.NotificationService.exceptions.InvalidMessageException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler (KafkaException.class)
    public void handleKafkaException(KafkaException ex) {
        logger.error("Kafka exception {}",ex.getMessage());
    }

    @ExceptionHandler (InvalidMessageException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMessageException(InvalidMessageException ex) {
        return new ResponseEntity<>(ex.getErrors(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(convertToMap("This method is not supported."),HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler (HttpMessageConversionException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        return new ResponseEntity<>(convertToMap("Request body is invalid."),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return new ResponseEntity<>(convertToMap("Something went wrong"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, String> convertToMap(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("Error message",message);
        map.put("timestamp",
                LocalDateTime
                        .now()
                        .toString());
        return map;
    }

}
