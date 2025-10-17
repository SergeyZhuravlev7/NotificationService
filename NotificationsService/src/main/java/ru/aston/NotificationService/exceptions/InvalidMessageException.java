package ru.aston.NotificationService.exceptions;

import java.util.Map;

public class InvalidMessageException extends RuntimeException {

    Map<String, String> errors;

    public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }


}
