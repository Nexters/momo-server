package com.nexters.momo.participation.domain;

import com.nexters.momo.generation.domain.Generation;
import com.nexters.momo.generation.domain.SignupCode;
import com.nexters.momo.member.domain.Member;
import com.nexters.momo.member.domain.Occupation;
import com.nexters.momo.member.domain.Role;
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

        Member member = new Member(
                "unique_id",
                "password",
                "shine",
                "010-1234-5678",
                Role.USER,
                Occupation.DEVELOPER,
                true
        );

        assertThatCode(() ->
                Participation.of(0L, generation, member)
        ).doesNotThrowAnyException();
    }
}