package com.nexters.momo.session.domain;

import com.nexters.momo.session.dto.PostSessionReqDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThatCode(() -> Session.createSession(new PostSessionReqDTO("세션 제목", LocalDateTime.now(),
                LocalDateTime.now(), "세션 주소"))).doesNotThrowAnyException();
    }

}