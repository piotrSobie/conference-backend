package com.example.conference.controllers;

import java.util.UUID;

import com.example.conference.exceptions.LoginAlreadyTakenException;
import com.example.conference.exceptions.UserNotExistException;
import com.example.conference.helpers.ErrorMessage;
import com.example.conference.models.User;
import com.example.conference.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/user")
@RestController
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
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

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(
                new ErrorMessage("Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateUserEmail(@PathVariable("id") UUID id, @RequestBody User user) {
        try {
            userService.updateUserEmail(id, user.getEmail());
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
}
