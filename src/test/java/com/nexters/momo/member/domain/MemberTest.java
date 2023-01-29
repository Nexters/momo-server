package com.nexters.momo.member.domain;

import com.nexters.momo.member.auth.domain.Authority;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.Occupation;
import com.nexters.momo.member.auth.domain.Role;
import com.nexters.momo.member.exception.InvalidUserEmailException;
import com.nexters.momo.member.exception.InvalidUserNameException;
import com.nexters.momo.member.exception.UserNotAgreePolicyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("도메인 : Member")
class MemberTest {

    @DisplayName("멤버 생성 테스트")
    @Test
    public void create_member() {
        assertThatCode(() -> new Member("shine@naver.com", "password", "shine", "device_unique_id", Role.USER, Occupation.DEVELOPER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("멤버 이메일 형식 검증 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"shine@navercom", "shine$naver.com", "shinenavercom"})
    public void member_invalid_email_id(String email) {
        assertThatThrownBy(() -> new Member(email, "password", "name", "device_unique_id", Role.USER, Occupation.DEVELOPER, true))
                .isInstanceOf(InvalidUserEmailException.class);
    }

    @DisplayName("멤버 비밀번호 일치 테스트")
    @Test
    public void member_password_match_test() {
        // given
        Member member = new Member("shine@naver.com", "password", "shine", "device_unique_id", Role.USER, Occupation.DEVELOPER, true);

        // when, then
        assertThat(member.isSamePassword("password")).isTrue();
    }

    @DisplayName("멤버 비밀번호 불일치 테스트")
    @Test
    public void member_password_not_match_test() {
        // given
        Member member = new Member("shine@naver.com", "password", "shine", "device_unique_id", Role.USER, Occupation.DEVELOPER, true);

        // when, then
        assertThat(member.isSamePassword("invalid-password")).isFalse();
    }

    @DisplayName("이름은 1글자 부터 20글자 까지 가능하다")
    @ParameterizedTest
    @ValueSource(strings = {"김", "김23456789김23456789"})
    public void member_name_length_test(String name) {
        assertThatCode(() -> new Member("shine@naver.com", "password", name, "device_unique_id", Role.USER, Occupation.DEVELOPER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("이름이 0글자 이거나 20글자 초과시 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(strings = {"", "김123456789김123456789김"})
    public void member_name_invalid_length_test(String name) {
        assertThatThrownBy(() -> new Member("shine@naver.com", "password", name, "device_unique_id", Role.USER, Occupation.DEVELOPER, true))
                        .isInstanceOf(InvalidUserNameException.class);
    }

    @DisplayName("개인 정보 동의를 하지 않은 멤버는 생성되지 않는다")
    @Test
    public void member_not_agree_policy_test() {
        assertThatThrownBy(() -> new Member("shine@naver.com", "password", "shine", "device_unique_id", Role.USER, Occupation.DEVELOPER, false))
                .isInstanceOf(UserNotAgreePolicyException.class);
    }

    @DisplayName("사용자의 활동 상태를 변경한다")
    @Test
    public void member_change_status_test() {
        // given
        Member member = new Member("shine@naver.com", "password", "name", "device_unique_id", Role.USER, Occupation.DEVELOPER, true);

        // when
        member.changeActive(true);

        // then
        assertThat(member.isActive()).isEqualTo(true);
    }

    @DisplayName("사용자는 권한을 Role로 갖는다")
    @Test
    public void member_role_test() {
        // given
        Member member = new Member("shine@naver.com", "password", "name", "device_unique_id", Role.USER, Occupation.DEVELOPER, true);

        // when
        Collection<? extends GrantedAuthority> authorities = member.getAuthorities();

        // then
        assertThat(authorities.contains(new Authority(Role.USER))).isTrue();
    }

    @DisplayName("사용자는 여러 권한을 Role로 갖을 수 있다")
    @Test
    public void member_roles_test() {
        // given
        Member member = new Member("shine@naver.com", "password", "name", "device_unique_id", Role.USER, Occupation.DEVELOPER, true);

        // when
        member.addRole(Role.ANONYMOUS);
        member.addRole(Role.ADMIN);

        // then
        assertThat(member.getAuthorities().containsAll(List.of(new Authority(Role.USER), new Authority(Role.ANONYMOUS), new Authority(Role.ADMIN)))).isTrue();
    }
}
