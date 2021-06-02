package com.example.conference.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.models.User;

public interface UserDao {
    
    User insertUser(UUID id, User user);

    default User insertUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> selectAllUsers();

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserById(UUID id);

    void updateUserEmail(UUID id, String email);

    void registerUserForPrelection(UUID userId, UUID lectureId);

    void deleteRegistration(UUID userId, UUID lectureId);
}
