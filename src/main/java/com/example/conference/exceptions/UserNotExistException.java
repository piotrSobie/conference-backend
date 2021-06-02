package com.example.conference.exceptions;

public class UserNotExistException extends Exception {
    public UserNotExistException() {
        super("User does not exist");
    }
}
