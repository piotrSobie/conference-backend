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
    private final LectureRowMapper lectureRowMapper;

    @Autowired
    public LectureDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.lectureRowMapper = new LectureRowMapper();
    }

    @Override
    public List<Lecture> selectAllLectures() {
        final String sql = "SELECT id, name, thematicPath, hStart, hEnd FROM lectures";
        return jdbcTemplate.query(sql, lectureRowMapper);
    }

    @Override
    public Optional<Lecture> findLectureById(UUID id) {
        Lecture l;
        try {
            final String sql = "SELECT id, name, thematicPath, hStart, hEnd FROM lectures WHERE id = ?";
            l = jdbcTemplate.queryForObject(sql, lectureRowMapper, id);
        } catch(EmptyResultDataAccessException e) {
            l = null;
        }

        return Optional.ofNullable(l);
    }

    @Override
    public int checkNrTakenSeats(UUID lectureId) {
        final String sql = "SELECT count(*) FROM UsersLecturesRelation WHERE LectureId = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, lectureId);
        return count;
    }

    @Override
    public List<Lecture> getRegistratedLecturesForUser(UUID userId) {
        final String sql = "SELECT l.id, l.name, l.thematicPath, l.hStart, l.hEnd  FROM lectures l " +
                            "INNER JOIN USERSLECTURESRELATION ul on l.id = ul.LECTUREID " + 
                            "INNER JOIN users u on ul.USERID = u.id WHERE u.id = ?";
        return jdbcTemplate.query(sql, lectureRowMapper, userId);
    }
}
