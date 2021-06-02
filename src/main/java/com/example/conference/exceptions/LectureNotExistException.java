package com.example.conference.exceptions;

public class LectureNotExistException extends Exception {
    public LectureNotExistException() {
        super("Lecture does not exist");
    }
}
