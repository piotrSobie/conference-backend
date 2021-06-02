package com.example.conference.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.example.conference.models.Lecture;

import org.springframework.jdbc.core.RowMapper;

public class LectureRowMapper implements RowMapper<Lecture> {

    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        Lecture lecture = new Lecture(
            UUID.fromString(rs.getString("id")), 
            rs.getString("name"),
            rs.getString("thematicPath"),
            rs.getString("hStart"),
            rs.getString("hEnd"));
        return lecture;
    }
}
