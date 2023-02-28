package com.nexters.momo.session.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Session JPA Repository 입니다.
 *
 * @author CHO Min Ho
 */
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findAllByGenerationId(Long generationId);

    @Query("select s.attendanceCode from Session s where s.id = :sessionId")
    Integer findAttendanceCodeById(@Param("sessionId") Long sessionId);
}
