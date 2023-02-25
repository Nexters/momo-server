package com.nexters.momo.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nexters.momo.session.domain.SessionImage.createSessionImage;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * SessionImage 엔티티 테스트입니다.
 *
 * @author CHO Min Ho
 */
@DisplayName("세션 이미지 도메인 테스트")
class SessionImageTest {

    @DisplayName("세션 이미지 생성 테스트")
    @Test
    void create_session_image() {
        assertThatCode(() -> createSessionImage(1L, "aaa.com", 1))
                .doesNotThrowAnyException();
    }

}