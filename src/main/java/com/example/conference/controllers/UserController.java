package com.example.conference.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.example.conference.dtos.AddUserDto;
import com.example.conference.dtos.UpdateUserEmailDto;
import com.example.conference.exceptions.AllSeatsTakenException;
import com.example.conference.exceptions.AlreadyRegisteredForHourException;
import com.example.conference.exceptions.LectureNotExistException;
import com.example.conference.exceptions.LoginAlreadyTakenException;
import com.example.conference.exceptions.UserNotExistException;
import com.example.conference.helpers.ErrorMessage;
import com.example.conference.models.Lecture;
import com.example.conference.models.User;
import com.example.conference.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/user")
@RestController
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(@RequestParam(required = false) Integer limit) {
        try {
            return new ResponseEntity<>(userService.getAllUsers(limit), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody AddUserDto user) {
        try {
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
        } catch(LoginAlreadyTakenException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.CONFLICT);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateUserEmail(@PathVariable("id") UUID id, @Valid @RequestBody UpdateUserEmailDto userDto) {
        try {
            userService.updateUserEmail(id, userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(UserNotExistException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.CONFLICT);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "lecture/{userId}")
    public ResponseEntity<Object> getLecturesForUser(@PathVariable("userId") UUID userId) {
        try {
            List<Lecture> lectures = userService.getRegisteredLecturesForUser(userId);
            return new ResponseEntity<>(
                lectures,
                HttpStatus.OK);
        } catch(UserNotExistException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "lecture/{lectureId}")
    public ResponseEntity<Object> registerForLecture(@PathVariable("lectureId") UUID lectureId, @RequestBody User user) {
        try {
            userService.registerUserForLecture(user.getId(), lectureId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(UserNotExistException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.NOT_FOUND);
        } catch(AllSeatsTakenException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.CONFLICT);
        } catch(AlreadyRegisteredForHourException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.CONFLICT);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "lecture/{lectureId}")
    public ResponseEntity<Object> deleteRegistration(@PathVariable("lectureId") UUID lectureId, @RequestBody User user) {
        try {
            userService.deleteRegistration(user.getId(), lectureId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(LectureNotExistException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.CONFLICT);
        } catch(UserNotExistException e) {
            return new ResponseEntity<>(
                new ErrorMessage(e.getMessage()),
                HttpStatus.CONFLICT);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
