package com.example.conference.exceptions;

public class AllSeatsTakenException extends Exception {
    public AllSeatsTakenException(String errorMessage) {
        super(errorMessage);
    }
}