package com.nexters.momo.session.application;

import com.nexters.momo.session.application.dto.SessionDto;
import com.nexters.momo.session.domain.Point;
import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.domain.SessionRepository;
import com.nexters.momo.session.exception.InvalidSessionIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @BeforeEach
    public void setup() {
        sessionRepository.deleteAll();
    }

    @DisplayName("세션 생성 테스트")
    @Test
    void create_session() {
        // given
        String expectedTitle = "세션 타이틀";
        String expectedContent = "세션 내용";
        String expectedAddress = "세션 주소";
        long expectedGenerationId = 22L;

        // when
        sessionService.createSession(
                SessionDto.of(0L, expectedTitle, 1, expectedContent,
                        LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100),
                        expectedAddress, "세부 주소", Point.of(100.20, 201.02),
                        LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)
                ),
                expectedGenerationId);

        // then
        List<Session> actuals = sessionRepository.findAll();
        assertThat(actuals.size()).isEqualTo(1);

        Session actual = actuals.get(0);
        assertThat(actual.getTitle()).isEqualTo(expectedTitle);
        assertThat(actual.getContent()).isEqualTo(expectedContent);
        assertThat(actual.getAddress()).isEqualTo(expectedAddress);
        assertThat(actual.getGenerationId()).isEqualTo(expectedGenerationId);
    }

    @DisplayName("세션 수정 테스트")
    @Test
    void update_session() {
        // given
        String updatedTitle = "새로운 세션 타이틀";
        Session given = sessionRepository.save(
                Session.createSession("세션", 1, "세션 내용",
                        LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(100),
                        "세션 주소", "세부 주소", Point.of(100.20, 201.02),
                        LocalDateTime.now().plusMinutes(50), LocalDateTime.now().plusMinutes(70), 22L)
        );

        // when
        sessionService.updateSession(given.getId(),
                SessionDto.of(given.getId(), updatedTitle, 1, "세션 내용",
                        LocalDateTime.now().minusMinutes(100), LocalDateTime.now().plusMinutes(100),
                        "세션 주소", "세부 주소", Point.of(100.20, 201.02),
                        LocalDateTime.now().minusMinutes(120), LocalDateTime.now().plusMinutes(100)
                )
        );

        // then
        Optional<Session> findSession = sessionRepository.findById(given.getId());
        assertThat(findSession.get().getTitle()).isEqualTo(updatedTitle);
    }

    @DisplayName("단일 세션 조회 테스트")
    @Test
    void read_single_session() {
        // given
        Session expected = sessionRepository.save(
                Session.createSession("세션", 1, "세션 내용",
                        LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(100),
                        "세션 주소", "세부 주소", Point.of(100.20, 201.02),
                        LocalDateTime.now().plusMinutes(50), LocalDateTime.now().plusMinutes(70), 22L));

        // when
        SessionDto actual = sessionService.getSingleSession(expected.getId());

        // then
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getWeek()).isEqualTo(expected.getWeek());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
    }

    @DisplayName("세션 리스트 조회 테스트")
    @Test
    void read_multiple_session_list() {
        // given
        int expected = 2;
        for (int i = 0; i < expected; i++) {
            sessionRepository.save(
                    Session.createSession("세션", 1, "세션 내용",
                            LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(100),
                            "세션 주소", "세부 주소", Point.of(100.20, 201.02),
                            LocalDateTime.now().plusMinutes(50), LocalDateTime.now().plusMinutes(70), 22L)
            );
        }

        // when
        List<SessionDto> sessionList = sessionService.getSessionList(22L);

        // then
        assertThat(sessionList.size()).isEqualTo(expected);
    }

    @DisplayName("세션 삭제 테스트")
    @Test
    void delete_session() {
        // given
        Session given = sessionRepository.save(
                Session.createSession("세션", 1, "세션 내용",
                        LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(100),
                        "세션 주소", "세부 주소", Point.of(100.20, 201.02),
                        LocalDateTime.now().plusMinutes(50), LocalDateTime.now().plusMinutes(70), 22L)
        );

        // when
        sessionService.deleteSession(given.getId());

        // then
        assertThat(sessionRepository.count()).isEqualTo(0);
        assertThatThrownBy(() -> sessionService.getSingleSession(given.getId())).isInstanceOf(InvalidSessionIdException.class);
    }

    @DisplayName("현재/ 곧 활동일 세션 조회 테스트")
    @Test
    void read_active_session() {
        // given
        long generationId = 22L;
        LocalDateTime current = LocalDateTime.now();

        Session before = sessionRepository.save(
                Session.createSession("지난 세션", 1, "과거",
                        current.minusDays(7), current.minusDays(6),
                        "과거입니다!", "세부 주소", Point.of(100.20, 201.02),
                        current.minusDays(7).plusMinutes(30), current.minusDays(7).plusMinutes(40), generationId)
        );

        Session active = sessionRepository.save(
                Session.createSession("현재 세션", 2, "활동중~!!",
                        current.minusMinutes(30), current.plusMinutes(100),
                        "현재입니다!", "세부 주소", Point.of(100.20, 201.02),
                        current.plusMinutes(50), current.plusMinutes(70), generationId)
        );

        Session after = sessionRepository.save(
                Session.createSession("미래 세션", 3, "미래",
                        current.plusMinutes(300), current.plusMinutes(500),
                        "미래입니다!", "세부 주소", Point.of(100.20, 201.02),
                        current.plusMinutes(350), current.plusMinutes(400), generationId)
        );

        // when
        SessionDto actual = sessionService.getActiveSession(generationId);

        // then
        assertThat(actual.getId()).isEqualTo(active.getId());
        assertThat(actual.getTitle()).isEqualTo(active.getTitle());
        assertThat(actual.getAddress()).isEqualTo(active.getAddress());
    }

}
