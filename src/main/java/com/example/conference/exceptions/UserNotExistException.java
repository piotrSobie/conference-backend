package com.example.conference.exceptions;

public class UserNotExistException extends Exception {
    public UserNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
