package com.nexters.momo.generation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Generation 도메인 테스트")
class GenerationTest {

    @DisplayName("기수 생성 테스트")
    @Test
    public void create_generation() {
        assertThatCode(() -> Generation.of(
                        0L,
                        SignupCode.from("signup_code"),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )
        ).doesNotThrowAnyException();
    }

    @DisplayName("활동 기수 여부 확인 - 현재 활동 기수임")
    @Test
    public void is_active_generation_returns_true() {
        // given
        LocalDateTime startedAt = LocalDateTime.now();
        LocalDateTime endedAt = LocalDateTime.now().plusMinutes(30);
        Generation generation = Generation.of(
                0L,
                SignupCode.from("signup_code"),
                startedAt,
                endedAt,
                LocalDateTime.now()
        );

        // when
        boolean actual = generation.isActive();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("활동 기수 여부 확인 - 현재 기수 아님")
    @Test
    public void is_active_generation_returns_false() {
        // given
        LocalDateTime startedAt = LocalDateTime.now().minusDays(30);
        LocalDateTime endedAt = LocalDateTime.now().minusDays(1);
        Generation generation = Generation.of(
                0L,
                SignupCode.from("signup_code"),
                startedAt,
                endedAt,
                LocalDateTime.now()
        );

        // when
        boolean actual = generation.isActive();

        // then
        assertThat(actual).isFalse();
    }

}