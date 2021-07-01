package com.example.conference.exceptions;

public class LectureNotExistException extends RuntimeException {
    public LectureNotExistException() {
        super("Lecture does not exist");
    }
}
