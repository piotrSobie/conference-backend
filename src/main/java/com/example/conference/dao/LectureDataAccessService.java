package com.example.conference.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.conference.mappers.LectureRowMapper;
import com.example.conference.models.Lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public Optional<Lecture> findLectureById(UUID id) {
        Lecture l;
        try {
            final String sql = "SELECT id, name, thematicPath, hStart, hEnd FROM lectures WHERE id = ?";
            l = jdbcTemplate.queryForObject(sql, new LectureRowMapper(), id);
        } catch(EmptyResultDataAccessException e) {
            l = null;
        }

        return Optional.ofNullable(l);
    }

    @Override
    public List<Lecture> getRegistratedLecturesForUser(UUID userId) {
        final String sql = "select l.id, l.name, l.THEMATICPATH, l.HSTART, l.HEND  from lectures l " +
                            "inner join USERSLECTURESRELATION ul on l.id = ul.LECTUREID " + 
                            "inner join users u on ul.USERID = u.id WHERE u.id=?";
        List<Lecture> registratedLecturesForUser = jdbcTemplate.query(sql, new LectureRowMapper(), userId);
        return registratedLecturesForUser;
    }
}
