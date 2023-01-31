package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.nexters.momo.acceptance.AuthStep.사용자_가입_요청;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_응답_확인;

public class AuthAcceptanceTest extends AcceptanceTest {

    /**
     * Given 해당 서비스에 회원가입이 되어있지 않은 이메일이 있다.
     * When 이메일을 통해 회원가입을 한다.
     * Then 사용자의 회원 가입이 완료된다.
     */
    @DisplayName("사용자 회원 가입 테스트")
    @Test
    void user_register_test() {
        // given
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest("shine@naver.com",
                "password", "Shine", 22, "developer", "device_uuid");

        // when
        var 사용자_가입_응답 = 사용자_가입_요청(memberRegisterRequest);

        // then
        사용자_가입_응답_확인(사용자_가입_응답, HttpStatus.CREATED);
    }
}
