package com.example.conference.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserDto {
    
    @NotBlank
    private final String login;
    @Email
    @NotBlank
    private final String email;

    public AddUserDto(@JsonProperty("login") String login, @JsonProperty("email") String email) {
        this.login = login;
        this.email = email;
    }  

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }
}
