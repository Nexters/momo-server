package com.nexters.momo.member.domain;

import com.nexters.momo.member.exception.InvalidUserNameException;
import com.nexters.momo.member.exception.InvalidUserPhoneException;
import com.nexters.momo.member.exception.UserNotAgreePolicyException;
import com.nexters.momo.team.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("도메인 : Member")
class MemberTest {

    @DisplayName("멤버 생성 테스트")
    @Test
    public void create_member() {
        assertThatCode(() -> new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, Occupation.DEVELOPER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("멤버 비밀번호 일치 테스트")
    @Test
    public void member_password_match_test() {
        // given
        Member member = new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, Occupation.DEVELOPER, true);

        // when, then
        assertThat(member.isSamePassword("password")).isTrue();
    }

    @DisplayName("멤버 비밀번호 불일치 테스트")
    @Test
    public void member_password_not_match_test() {
        // given
        Member member = new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, Occupation.DEVELOPER, true);

        // when, then
        assertThat(member.isSamePassword("invalid-password")).isFalse();
    }

    @DisplayName("이름은 1글자 부터 20글자 까지 가능하다")
    @ParameterizedTest
    @ValueSource(strings = {"김", "김23456789김23456789"})
    public void member_name_length_test(String name) {
        assertThatCode(() -> new Member("unique_id", "password", name, "010-1234-5678", Role.USER, Occupation.DEVELOPER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("이름이 0글자 이거나 20글자 초과시 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(strings = {"", "김123456789김123456789김"})
    public void member_name_invalid_length_test(String name) {
        assertThatThrownBy(() -> new Member("unique_id", "password", name, "010-1234-5678", Role.USER, Occupation.DEVELOPER, true))
                        .isInstanceOf(InvalidUserNameException.class);
    }

    @DisplayName("전화번호는 11글자 부터 13글자 까지 가능하다")
    @ParameterizedTest
    @ValueSource(strings = {"02-123-1234", "010-1234-5678"})
    public void phone_numbers_length_test(String phone) {
        assertThatCode(() -> new Member("unique_id", "password", "name", phone, Role.USER, Occupation.DEVELOPER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("전화번호가 10글자 이하거나 작거나 14글자 이상이면 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(strings = {"02-123-123", "010-1234-56789"})
    public void phone_numbers_invalid_length_test(String phone) {
        assertThatThrownBy(() -> new Member("unique_id", "password", "name", phone, Role.USER, Occupation.DEVELOPER, true))
                .isInstanceOf(InvalidUserPhoneException.class);
    }

    @DisplayName("개인 정보 동의를 하지 않은 멤버는 생성되지 않는다")
    @Test
    public void member_not_agree_policy_test() {
        assertThatThrownBy(() -> new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, Occupation.DEVELOPER, false))
                .isInstanceOf(UserNotAgreePolicyException.class);
    }

    @DisplayName("사용자의 활동 상태를 변경한다")
    @Test
    public void member_change_status_test() {
        // given
        Member member = new Member("unique_id", "password", "name", "010-1234-5678", Role.USER, Occupation.DEVELOPER, true);

        // when
        member.changeStatus(MemberStatus.SUSPEND);

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.SUSPEND);
    }

    @DisplayName("멤버에게 성공적으로 팀을 추가할 수 있다")
    @Test
    public void member_join_team_test() {
        // given
        Member member = createMember(1L, "member_1");

        // when
        Team team1 = createTeam(1L);
        Team team2 = createTeam(2L);
        member.addTeam(team1);
        member.addTeam(team2);

        // then
        assertThat(member.getAllTeamId()).containsAll(List.of(1L, 2L));
        assertThat(team1.getAllMemberId()).containsExactly(1L);
        assertThat(team2.getAllMemberId()).containsExactly(1L);
    }

    private Team createTeam(Long id) {
        Team team = new Team("team_name", 1234L);
        ReflectionTestUtils.setField(team, "id", id);
        return team;
    }

    private Member createMember(Long id, String userId) {
        Member member = new Member(userId, "password", "shine", "010-1234-5678", Role.USER, Occupation.DEVELOPER, true);
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
