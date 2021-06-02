package com.example.conference.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.dao.LectureDataAccessService;
import com.example.conference.dao.UserDao;
import com.example.conference.dao.UsersLecturesRelationDao;
import com.example.conference.exceptions.AllSeatsTakenException;
import com.example.conference.exceptions.AlreadyRegisteredForHourException;
import com.example.conference.exceptions.LectureNotExistException;
import com.example.conference.exceptions.LoginAlreadyTakenException;
import com.example.conference.exceptions.UserNotExistException;
import com.example.conference.models.Lecture;
import com.example.conference.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final UsersLecturesRelationDao usersLecturesRelationDao;
    private final LectureDataAccessService lectureDataAccessService;
    private final LectureService lectureService;

    @Autowired
    public UserService(@Qualifier("h2-user") UserDao userDao,
                        @Qualifier("h2-users-lectures-relation") UsersLecturesRelationDao usersLecturesRelationDao,
                        @Qualifier("h2-lecture") LectureDataAccessService lectureDataAccessService,
                        LectureService lectureService) {
        this.userDao = userDao;
        this.usersLecturesRelationDao = usersLecturesRelationDao;
        this.lectureDataAccessService = lectureDataAccessService;
        this.lectureService = lectureService;
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

    public int registerUserForLecture(UUID userId, UUID lectureId) 
        throws LectureNotExistException, UserNotExistException, AllSeatsTakenException, AlreadyRegisteredForHourException {
        Lecture lecture = lectureService.getLectureById(lectureId);
        Optional<User> userOptional = userDao.findUserById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException("Uzytkownik nie istnieje");
        }
        
        int takenSeats = usersLecturesRelationDao.checkNrTakenSeats(lectureId);
        if(takenSeats >= LectureService.MAX_SEAT_NR) {
            throw new AllSeatsTakenException("All seats taken for this lecture");
        }

        List<Lecture> registeredUserLectures = lectureDataAccessService.getRegistratedLecturesForUser(userId);
        for(Lecture l : registeredUserLectures) {
            if(l.getHStart() == lecture.getHStart()) {
                throw new AlreadyRegisteredForHourException("Juz zapisano na ta godzine");
            }
        }

        usersLecturesRelationDao.registerUserForPrelection(userId, lectureId);
        
        return 0;
    }

    public List<Lecture> getRegisteredLecturesForUser(UUID userId) throws UserNotExistException {
        Optional<User> userOptional = userDao.findUserById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException("Uzytkownik nie istnieje");
        }

        return lectureDataAccessService.getRegistratedLecturesForUser(userId);
    } 
    
}
