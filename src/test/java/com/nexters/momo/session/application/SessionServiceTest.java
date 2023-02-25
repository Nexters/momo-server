package com.nexters.momo.session.application;

import com.nexters.momo.session.application.SessionService;
import com.nexters.momo.session.application.dto.SessionDto;
import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.exception.InvalidSessionIdException;
import com.nexters.momo.session.domain.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

/**
 * Session Service 관련 테스트 클래스입니다.
 *
 * @author CHO Min Ho
 */
@SpringBootTest
@Transactional
@DisplayName("세션 서비스 테스트")
class SessionServiceTest {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @DisplayName("세션 생성 테스트")
    @Test
    void create_session() {
        // when
        Long sessionId = sessionService.createSession(new SessionDto(0L, "세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)), 1L);

        // then
        Optional<Session> findSession = sessionRepository.findById(sessionId);
        assertThat(findSession).isNotEmpty();
        assertThat(findSession.get().getId()).isEqualTo(sessionId);
    }

    @DisplayName("세션 수정 테스트")
    @Test
    void update_session() {
        // given
        Long sessionId = sessionService.createSession(new SessionDto(0L, "세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)), 1L);

        // when
        sessionService.updateSession(sessionId, new SessionDto(sessionId, "새로운 세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)));

        // then
        Optional<Session> findSession = sessionRepository.findById(sessionId);
        assertThat(findSession.get().getTitle()).isEqualTo("새로운 세션 타이틀");
    }

    @DisplayName("단일 세션 조회 테스트")
    @Test
    void read_single_session() {
        // given
        Long sessionId = sessionService.createSession(new SessionDto(0L, "세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)), 1L);

        // when
        SessionDto res = sessionService.getSingleSession(sessionId);

        // then
        assertThat(res.getTitle()).isEqualTo("세션 타이틀");
    }

    @DisplayName("세션 리스트 조회 테스트")
    @Test
    void read_multiple_session_list() {
        // given
        sessionService.createSession(new SessionDto(0L, "첫번째 세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)), 1L);
        sessionService.createSession(new SessionDto(0L, "두번째 세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)), 1L);

        // when
        List<SessionDto> sessionList = sessionService.getSessionList(1L);
        Set<String> keywordSet = new HashSet<>();
        for (SessionDto session : sessionList) {
            keywordSet.add(session.getTitle());
        }

        // then
        assertThat(sessionList.size()).isEqualTo(2);
        assertThat(keywordSet.size()).isEqualTo(2);
    }

    @DisplayName("세션 삭제 테스트")
    @Test
    void delete_session() {
        // given
        Long sessionId = sessionService.createSession(new SessionDto(0L, "첫번째 세션 타이틀", 1, "세션 내용",
                LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100), "세션 주소",
                LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)), 1L);

        // when
        sessionService.deleteSession(sessionId);

        // then
        assertThatThrownBy(() -> sessionService.getSingleSession(sessionId))
                .isInstanceOf(InvalidSessionIdException.class);
    }

}
