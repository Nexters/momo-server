package com.nexters.momo.member.domain;

import com.nexters.momo.member.exception.InvalidUserNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

class MemberTest {

    @DisplayName("멤버 생성 테스트")
    @Test
    public void create_member() {
        assertThatCode(() -> new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("멤버 비밀번호 일치 테스트")
    @Test
    public void member_password_match_test() {
        // given
        Member member = new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, true);

        // when, then
        Assertions.assertThat(member.isSamePassword("password")).isTrue();
    }

    @DisplayName("멤버 비밀번호 불일치 테스트")
    @Test
    public void member_password_not_match_test() {
        // given
        Member member = new Member("unique_id", "password", "shine", "010-1234-5678", Role.USER, true);

        // when, then
        Assertions.assertThat(member.isSamePassword("invalid-password")).isFalse();
    }

    @DisplayName("이름은 1글자 부터 20글자 까지 가능하다")
    @ParameterizedTest
    @ValueSource(strings = {"김", "김23456789김23456789"})
    public void member_name_length_test(String name) {
        assertThatCode(() -> new Member("unique_id", "password", name, "010-1234-5678", Role.USER, true))
                .doesNotThrowAnyException();
    }

    @DisplayName("이름이 0글자 이거나 20글자 초과시 생성에 실패한다")
    @ParameterizedTest
    @ValueSource(strings = {"", "김123456789김123456789김"})
    public void member_name_invalid_length_test(String name) {
        Assertions.assertThatThrownBy(() -> new Member("unique_id", "password", name, "010-1234-5678", Role.USER, true))
                        .isInstanceOf(InvalidUserNameException.class);
    }
}
