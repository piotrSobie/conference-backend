package com.example.conference.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.dao.UserDao;
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
    private final LectureService lectureService;

    private final static String notificationsFileName = "powiadomienia.txt";

    @Autowired
    public UserService(@Qualifier("h2-user") UserDao userDao,
                        LectureService lectureService) {
        this.userDao = userDao;
        this.lectureService = lectureService;
    }

    public User addUser(User user) throws LoginAlreadyTakenException {
        Optional<User> userOptional = userDao.findUserByLogin(user.getLogin());
        if(userOptional.isPresent()) {
            throw new LoginAlreadyTakenException();
        }
        return userDao.insertUser(user);
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public void updateUserEmail(UUID id, String email) throws UserNotExistException {
        Optional<User> userOptional = userDao.findUserById(id);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException();
        }
        userDao.updateUserEmail(id, email);
    }

    public void registerUserForLecture(UUID userId, UUID lectureId) 
        throws LectureNotExistException, UserNotExistException, AllSeatsTakenException,
        AlreadyRegisteredForHourException, IOException {
        
        Lecture lecture = lectureService.getLectureById(lectureId);
        Optional<User> userOptional = userDao.findUserById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException();
        }
        
        int takenSeats = lectureService.getNrTeakenSeatsLecture(lectureId);
        if(takenSeats >= LectureService.MAX_SEAT_NR) {
            throw new AllSeatsTakenException();
        }

        List<Lecture> registeredUserLectures = lectureService.getRegistratedLecturesForUser(userId);
        for(Lecture l : registeredUserLectures) {
            if(l.getHStart() == lecture.getHStart()) {
                throw new AlreadyRegisteredForHourException();
            }
        }

        userDao.registerUserForPrelection(userId, lectureId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
            
        List<String> message = Arrays.asList(
            "Data wyslania " + dtf.format(now),
            "Do " + userOptional.get().getLogin(),
            "Dziekujemy za zarejestrowanie sie na wyklad " + lecture.getName() + ". Rozpoczyna sie o " + lecture.getHStart() + "\n" 
        );
        Path file = Paths.get(UserService.notificationsFileName);
        if (Files.exists(file)) {
            Files.write(file, message, StandardOpenOption.APPEND);
        } else {
            Files.write(file, message, StandardCharsets.UTF_8);
        }
    }

    public List<Lecture> getRegisteredLecturesForUser(UUID userId) throws UserNotExistException {
        Optional<User> userOptional = userDao.findUserById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException();
        }

        return lectureService.getRegistratedLecturesForUser(userId);
    }
    
    public void deleteRegistration(UUID userId, UUID lectureId) throws UserNotExistException, LectureNotExistException {
        Optional<User> userOptional = userDao.findUserById(userId);
        if(userOptional.isEmpty()) {
            throw new UserNotExistException();
        }
        lectureService.getLectureById(lectureId);

        userDao.deleteRegistration(userId, lectureId);
    } 
    
}
