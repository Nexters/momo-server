package com.nexters.momo.member.domain;

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
}
