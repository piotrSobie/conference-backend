package com.example.conference.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.example.conference.models.User;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
            UUID.fromString(rs.getString("id")),
            rs.getString("login"),
            rs.getString("email"));
        return user;
    }
    
}
