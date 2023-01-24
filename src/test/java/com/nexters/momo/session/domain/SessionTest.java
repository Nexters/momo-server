package com.nexters.momo.session.domain;

import com.nexters.momo.session.dto.PostSessionReqDto;
import com.nexters.momo.session.exception.InvalidSessionTimeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

/**
 * Session 엔티티 관련 테스트 클래스입니다.
 *
 * @author CHO Min Ho
 */
@DisplayName("세션 생성 테스트")
class SessionTest {

    @DisplayName("세션 생성 테스트")
    @Test
    public void create_session() {
        assertThatCode(() -> Session.createSession(new PostSessionReqDto("세션 제목", LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(100), "세션 주소"))).doesNotThrowAnyException();
    }

    @DisplayName("세션 시각 예외 테스트")
    @Test
    public void invalid_session_time() {
        Assertions.assertThatThrownBy(() -> Session.createSession(new PostSessionReqDto("세션 제목", LocalDateTime.now(),
                LocalDateTime.now().minusMinutes(100), "세션 주소")))
                .isInstanceOf(InvalidSessionTimeException.class);
    }

}