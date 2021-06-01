package com.example.conference.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lecture {

    private final UUID id;
    private final String name;
    private final String thematicPath;
    private final String hStart;
    private final String hEnd;

    public Lecture(@JsonProperty("id") UUID id,
                   @JsonProperty("name") String name,
                   @JsonProperty("thematicPath") String thematicPath,
                   @JsonProperty("hStart") String hStart,
                   @JsonProperty("hEnd") String hEnd) {
        this.id = id;
        this.name = name;
        this.thematicPath = thematicPath;
        this.hStart = hStart;
        this.hEnd = hEnd;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getThematicPath() {
        return this.thematicPath;
    }

    public String getHStart() {
        return this.hStart;
    }

    public String getHEnd() {
        return this.hEnd;
    }
    
}
