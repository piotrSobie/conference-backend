package com.example.conference.controllers;

import com.example.conference.helpers.ErrorMessage;
import com.example.conference.services.LectureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/lecture")
@RestController
public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllLectures() {
        try {
            return new ResponseEntity<>(lectureService.getAllLectures(), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
     
}
