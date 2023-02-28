package com.nexters.momo.session.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Session JPA Repository 입니다.
 *
 * @author CHO Min Ho
 */
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findAllByGenerationId(Long generationId);
}
