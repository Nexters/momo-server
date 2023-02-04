package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.nexters.momo.acceptance.AuthStep.로그인_요청;
import static com.nexters.momo.acceptance.AuthStep.로그인_응답_확인;
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
                "password", "Shine", 22, "developer", "uuid");

        // when
        var 사용자_가입_응답 = 사용자_가입_요청(memberRegisterRequest);

        // then
        사용자_가입_응답_확인(사용자_가입_응답, HttpStatus.CREATED);
    }

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When 로그인을 시도하면
     * Then 성공적으로 로그인하고 jwt token을 반환한다
     */
    @DisplayName("사용자 로그인 테스트")
    @Test
    void bearer_token_login() {
        // when
        var response = 로그인_요청(USER_EMAIL, USER_PASSWORD, DEVICE_UUID);

        // then
        로그인_응답_확인(response, HttpStatus.OK);
    }
}
