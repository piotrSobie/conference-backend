package com.example.conference.exceptions;

public class AlreadyRegisteredForHourException extends Exception {
    public AlreadyRegisteredForHourException() {
        super("Already registered for this hour");
    }
}
