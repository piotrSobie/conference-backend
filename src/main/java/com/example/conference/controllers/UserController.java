package com.example.conference.controllers;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<String> addUser(@RequestBody User user) {
        int result = userService.addUser(user);
        if (result == 409) {
            return new ResponseEntity<>(
                "Podany login jest juz zajety",
                HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(path = "{id}")
    public void updateUserEmail(@PathVariable("id") UUID id, @RequestBody User user) {
        userService.updateUserEmail(id, user.getEmail());
    }
}
