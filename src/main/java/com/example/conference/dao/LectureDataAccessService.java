package com.example.conference.dao;

import java.util.List;

import com.example.conference.mappers.LectureRowMapper;
import com.example.conference.models.Lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("h2-lecture")
public class LectureDataAccessService implements LectureDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LectureDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Lecture> selectAllLectures() {
        final String sql = "SELECT id, name, thematicPath, hStart, hEnd FROM lectures";
        List<Lecture> lectures = jdbcTemplate.query(sql, new LectureRowMapper());
        return lectures;
    }
    
}
