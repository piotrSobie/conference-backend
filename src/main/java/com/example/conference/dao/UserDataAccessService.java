package com.example.conference.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.mappers.UserRowMapper;
import com.example.conference.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("h2")
public class UserDataAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertUser(UUID id, User user) {
        final String sql = "INSERT INTO users (login, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getLogin(), user.getEmail());
        return 0;
    }

    @Override
    public List<User> selectAllUsers() {
        final String sql = "SELECT id, login, email FROM USERS";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        return users;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        User u;
        try {
            final String sql = "SELECT id, login, email FROM users WHERE login = ?";
            u = jdbcTemplate.queryForObject(sql, new UserRowMapper(), login);
        } catch(EmptyResultDataAccessException e) {
            u = null;
        }
        return Optional.ofNullable(u);
    }

    @Override
    public int updateUserEmail(UUID id, String email) {
        final String sql = "UPDATE USERS SET email = ? WHERE id = ?";
        jdbcTemplate.update(sql, email, id);
        return 0;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        User u;
        try {
            final String sql = "SELECT id, login, email FROM users WHERE id = ?";
            u = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch(EmptyResultDataAccessException e) {
            u = null;
        }
        return Optional.ofNullable(u);
    }
    
}
