package com.example.conference.exceptions;

public class LectureNotExistException extends Exception {
    public LectureNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
