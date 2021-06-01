package com.example.conference.services;

import java.util.List;

import com.example.conference.dao.LectureDao;
import com.example.conference.models.Lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    private final LectureDao lectureDao;

    @Autowired
    public LectureService(@Qualifier("h2-lecture") LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    public List<Lecture> getAllLectures() {
        return lectureDao.selectAllLectures();
    }
    
}
