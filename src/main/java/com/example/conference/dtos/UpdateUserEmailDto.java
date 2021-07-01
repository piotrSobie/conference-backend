package com.example.conference.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserEmailDto {

    @Email
    @NotBlank
    private final String email;

    public UpdateUserEmailDto(@JsonProperty("email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
    
}
