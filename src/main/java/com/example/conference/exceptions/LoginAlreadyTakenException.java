package com.example.conference.exceptions;

public class LoginAlreadyTakenException extends Exception {
    public LoginAlreadyTakenException() {
        super("Login already taken");
    }
}
