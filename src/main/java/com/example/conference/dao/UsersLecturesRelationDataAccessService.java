package com.example.conference.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("h2-users-lectures-relation")
public class UsersLecturesRelationDataAccessService implements UsersLecturesRelationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersLecturesRelationDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int registerUserForPrelection(UUID userId, UUID lectureId) {
        final String sql = "INSERT INTO UsersLecturesRelation (UserId, LectureId) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, lectureId);
        return 0;
    }

    @Override
    public int checkNrTakenSeats(UUID lectureId) {
        final String sql = "SELECT count(*) FROM UsersLecturesRelation WHERE LectureId = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, lectureId);
        return count;
    }
    
}
