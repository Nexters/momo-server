package com.nexters.momo.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
