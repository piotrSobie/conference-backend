package com.example.conference.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.models.Lecture;

public interface LectureDao {
    List<Lecture> selectAllLectures();
    Optional<Lecture> findLectureById(UUID id);
    List<Lecture> getRegistratedLecturesForUser(UUID userId);
    
}
