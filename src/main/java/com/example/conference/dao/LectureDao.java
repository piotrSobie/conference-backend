package com.example.conference.dao;

import java.util.List;

import com.example.conference.models.Lecture;

public interface LectureDao {
    List<Lecture> selectAllLectures();
    
}
