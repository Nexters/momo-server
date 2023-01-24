package com.nexters.momo.team.domain;

import com.nexters.momo.member.domain.Member;
import com.nexters.momo.member.domain.Role;
import com.nexters.momo.team.exception.InvalidTeamNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("팀에 성공적으로 멤버를 추가할 수 있다")
    @Test
    public void team_add_member_test() {
        // given
        Team newTeam = new Team("team_name", 1234L);

        // when
        newTeam.addMember(createMember(1L, "member_1"));
        newTeam.addMember(createMember(2L, "member_2"));

        // then
        assertThat(newTeam.getAllMemberId()).containsAll(List.of(1L, 2L));
    }

    @DisplayName("팀에서 멤버를 삭제할 수 있다.")
    @Test
    public void team_delete_member_test() {
        // given
        Team newTeam = createTeam(7L);
        Member member1 = createMember(1L, "member_1");
        Member member2 = createMember(2L, "member_2");
        newTeam.addMember(member1);
        newTeam.addMember(member2);

        // when
        newTeam.deleteMember(member1);

        // then
        assertThat(newTeam.getAllMemberId()).containsExactly(2L);
        assertThat(member1.getAllTeamId()).containsExactly(7L);
        assertThat(member2.getAllTeamId()).containsExactly(7L);
    }

    private Team createTeam(Long id) {
        Team team = new Team("team_name", 1234L);
        ReflectionTestUtils.setField(team, "id", id);
        return team;
    }

    private Member createMember(Long id, String userId) {
        Member member = new Member(userId, "password", "shine", "010-1234-5678", Role.USER, true);
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
