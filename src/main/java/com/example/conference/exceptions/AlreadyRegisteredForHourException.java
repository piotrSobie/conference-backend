package com.example.conference.exceptions;

public class AlreadyRegisteredForHourException extends Exception {
    public AlreadyRegisteredForHourException(String errorMessage) {
        super(errorMessage);
    }
}
