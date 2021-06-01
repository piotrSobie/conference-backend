package com.example.conference.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private final UUID id;
    private final String login;
    private final String email;

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("login") String login,
                @JsonProperty("email") String email) {
        
        this.id = id;
        this.login = login;
        this.email = email;
    }

    public UUID getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }
    
}
