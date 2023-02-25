package com.nexters.momo.session.application;

import com.nexters.momo.session.domain.SessionImage;
import com.nexters.momo.session.domain.SessionImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("세션 이미지 서비스 테스트")
class SessionImageServiceTest {

    @Autowired
    private SessionImageService sessionImageService;

    @Autowired
    private SessionImageRepository sessionImageRepository;

    @Test
    @DisplayName("세션 이미지 삭제 테스트")
    void delete_session_image() {
        // given
        sessionImageRepository.save(SessionImage.createSessionImage(1L, "aaa.com", 1));
        sessionImageRepository.save(SessionImage.createSessionImage(1L, "bbb.com", 2));

        // when
        sessionImageService.deleteSessionImage(1L);

        // then
        assertThat(sessionImageRepository.findBySessionId(1L).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("세션 이미지 조회 테스트")
    void get_session_image() {
        // given
        sessionImageRepository.save(SessionImage.createSessionImage(1L, "aaa.com", 1));
        sessionImageRepository.save(SessionImage.createSessionImage(1L, "bbb.com", 2));

        // when
        List<String> sessionImageList = sessionImageService.getSessionImageList(1L);

        // then
        assertThat(sessionImageList.size()).isEqualTo(2);
    }

}