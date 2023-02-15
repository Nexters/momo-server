package com.nexters.momo.generation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Generation 도메인 테스트")
class GenerationTest {

    @DisplayName("기수 생성 테스트")
    @Test
    public void create_generation() {
        assertThatCode(() ->
                Generation.of(0L, 22, SignupCode.from("signup_code"), true)
        ).doesNotThrowAnyException();
    }

    @DisplayName("활동 기수 여부 확인 - 현재 활동 기수임")
    @Test
    public void is_active_generation_returns_true() {
        // given
        Generation generation = Generation.of(0L, 22, SignupCode.from("signup_code"), true);

        // when
        boolean actual = generation.isActive();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("활동 기수 여부 확인 - 현재 기수 아님")
    @Test
    public void is_active_generation_returns_false() {
        // given
        Generation generation = Generation.of(0L, 22, SignupCode.from("signup_code"), false);

        // when
        boolean actual = generation.isActive();

        // then
        assertThat(actual).isFalse();
    }

}