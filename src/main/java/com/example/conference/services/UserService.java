package com.example.conference.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.dao.UserDao;
import com.example.conference.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("h2") UserDao userDao) {
        this.userDao = userDao;
    }

    public int addUser(User user) {
        Optional<User> userOptional = userDao.findUserByLogin(user.getLogin());
        if(userOptional.isPresent()) {
            return 409;
        }

        return userDao.insertUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public int updateUserEmail(UUID id, String email) {
        return userDao.updateUserEmail(id, email);
    }
    
}
