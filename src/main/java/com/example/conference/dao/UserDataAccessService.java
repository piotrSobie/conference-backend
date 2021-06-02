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

@Repository("h2-user")
public class UserDataAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    
    @Autowired
    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = new UserRowMapper();
    }

    @Override
    public User insertUser(UUID id, User user) {
        final String sql = "INSERT INTO users (id, login, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, user.getLogin(), user.getEmail());
        return new User(id, user.getLogin(), user.getEmail());
    } 

    @Override
    public List<User> selectAllUsers() {
        final String sql = "SELECT id, login, email FROM USERS";
        List<User> users = jdbcTemplate.query(sql, userRowMapper);
        return users;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        User u;
        try {
            final String sql = "SELECT id, login, email FROM users WHERE login = ?";
            u = jdbcTemplate.queryForObject(sql, userRowMapper, login);
        } catch(EmptyResultDataAccessException e) {
            u = null;
        }
        return Optional.ofNullable(u);
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        User u;
        try {
            final String sql = "SELECT id, login, email FROM users WHERE id = ?";
            u = jdbcTemplate.queryForObject(sql, userRowMapper, id);
        } catch(EmptyResultDataAccessException e) {
            u = null;
        }
        return Optional.ofNullable(u);
    }

    @Override
    public void updateUserEmail(UUID id, String email) {
        final String sql = "UPDATE USERS SET email = ? WHERE id = ?";
        jdbcTemplate.update(sql, email, id);
    }

    @Override
    public void registerUserForPrelection(UUID userId, UUID lectureId) {
        final String sql = "INSERT INTO UsersLecturesRelation (UserId, LectureId) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, lectureId);
    }

    @Override
    public void deleteRegistration(UUID userId, UUID lectureId) {
        final String sql = "DELETE FROM UsersLecturesRelation WHERE UserId = ? AND LectureId = ?";
        jdbcTemplate.update(sql, userId, lectureId);
    }
}
