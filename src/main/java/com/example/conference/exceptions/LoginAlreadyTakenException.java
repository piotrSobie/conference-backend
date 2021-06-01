package com.example.conference.exceptions;

public class LoginAlreadyTakenException extends Exception {
    public LoginAlreadyTakenException(String errorMessage) {
        super(errorMessage);
    }
}
