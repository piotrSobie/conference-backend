package com.example.conference.dao;

import java.util.UUID;

public interface UsersLecturesRelationDao {
    
    int registerUserForPrelection(UUID userId, UUID lectureId);

    int checkNrTakenSeats(UUID lectureId);
}
