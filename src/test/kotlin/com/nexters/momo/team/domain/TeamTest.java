package com.nexters.momo.team.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("도메인 : Team")
class TeamTest {

    @DisplayName("팀 생성 테스트")
    @Test
    public void create_team() {
        assertThatCode(() -> new Team("OVN", 1234L))
                .doesNotThrowAnyException();
    }
}
