package com.example.conference.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.dao.LectureDao;
import com.example.conference.exceptions.LectureNotExistException;
import com.example.conference.models.Lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    private final LectureDao lectureDao;

    public final static int MAX_SEAT_NR = 5;

    @Autowired
    public LectureService(@Qualifier("h2-lecture") LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    public List<Lecture> getAllLectures() {
        return lectureDao.selectAllLectures();
    }

    Lecture getLectureById(UUID id) throws LectureNotExistException {
        Optional<Lecture> lectureOptional = lectureDao.findLectureById(id);
        if(lectureOptional.isEmpty()) {
            throw new LectureNotExistException();
        }
        return lectureOptional.get();
    }
    
}
