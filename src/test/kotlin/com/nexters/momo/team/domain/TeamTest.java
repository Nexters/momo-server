package com.nexters.momo.team.domain;

import com.nexters.momo.team.exception.InvalidTeamNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("도메인 : Team")
class TeamTest {

    @DisplayName("팀 생성 테스트")
    @Test
    public void create_team() {
        assertThatCode(() -> new Team("OVN", 1234L))
                .doesNotThrowAnyException();
    }

    @DisplayName("팀 이름은 1글자 부터 30글자 까지 가능하다")
    @ParameterizedTest
    @ValueSource(strings = {"팀", "팀23456789팀23456789팀23456789"})
    public void team_name_length_test(String teamName) {
        assertThatCode(() -> new Team(teamName, 1234L))
                .doesNotThrowAnyException();
    }

    @DisplayName("팀 이름이 0글자 이거나 30글자 초과시 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(strings = {"", "팀123456789팀123456789팀123456789팀"})
    public void team_name_invalid_length_test(String teamName) {
        assertThatThrownBy(() -> new Team(teamName, 1234L))
                .isInstanceOf(InvalidTeamNameException.class);
    }
}
