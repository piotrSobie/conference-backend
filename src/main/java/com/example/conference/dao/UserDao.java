package com.example.conference.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.dtos.AddUserDto;
import com.example.conference.dtos.UpdateUserEmailDto;
import com.example.conference.models.User;

public interface UserDao {
    
    User insertUser(UUID id, AddUserDto user);

    default User insertUser(AddUserDto user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> selectAllUsers(Integer limit);

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserById(UUID id);

    void updateUserEmail(UUID id, UpdateUserEmailDto email);

    void registerUserForPrelection(UUID userId, UUID lectureId);

    void deleteRegistration(UUID userId, UUID lectureId);
}
