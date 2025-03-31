package org.example.services;

public class UserNotSavedException extends RuntimeException {
    public UserNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserNotSavedException(String message) {
        super(message);
    }
}

