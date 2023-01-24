package com.nexters.momo.participation.domain;

import com.nexters.momo.generation.domain.Generation;
import com.nexters.momo.generation.domain.SignupCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("Participation 도메인 테스트")
class ParticipationTest {

    @DisplayName("참여 테스트")
    @Test
    public void create_participation() {
        Generation generation = Generation.of(
                0L,
                SignupCode.from("signup_code"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        assertThatCode(() ->
                Participation.of(0L, generation, Participation.Position.DESIGNER)
        ).doesNotThrowAnyException();
    }
}