package com.nexters.momo.session.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * SessionImage JPA Repository 입니다.
 *
 * @author CHO Min Ho
 */
public interface SessionImageRepository extends JpaRepository<SessionImage, Long> {
    List<SessionImage> findBySessionId(Long id);
    void deleteBySessionId(Long id);
}
