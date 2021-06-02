package com.example.conference.exceptions;

public class AllSeatsTakenException extends Exception {
    public AllSeatsTakenException() {
        super("All seats taken for this lecture");
    }
}