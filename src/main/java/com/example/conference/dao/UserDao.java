package com.example.conference.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.models.User;

public interface UserDao {
    
    int insertUser(UUID id, User user);

    default int insertUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> selectAllUsers();

    Optional<User> findUserByLogin(String login);

    int updateUserEmail(UUID id, String email);

    Optional<User> findUserById(UUID id);
}
