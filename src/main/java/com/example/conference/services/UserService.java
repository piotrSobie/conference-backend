package com.example.conference.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.dao.UserDao;
import com.example.conference.exceptions.LoginAlreadyTakenException;
import com.example.conference.exceptions.UserNotExistException;
import com.example.conference.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("h2-user") UserDao userDao) {
        this.userDao = userDao;
    }

    public int addUser(User user) throws LoginAlreadyTakenException {
        Optional<User> userOptional = userDao.findUserByLogin(user.getLogin());
        if(userOptional.isPresent()) {
            throw new LoginAlreadyTakenException("Podany login jest juz zajety");
        }

        return userDao.insertUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public int updateUserEmail(UUID id, String email) throws UserNotExistException {
        Optional<User> userOptional = userDao.findUserById(id);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException("Uzytkownik nie istnieje");
        }
        return userDao.updateUserEmail(id, email);
    }
    
}
