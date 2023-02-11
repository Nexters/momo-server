package com.nexters.momo.session.repository;

import com.nexters.momo.session.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Session JPA Repository 입니다.
 *
 * @author CHO Min Ho
 */
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findSessionByGenerationId(Long generationId);
}
