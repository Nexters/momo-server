package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.domain.MemberRepository;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.nexters.momo.acceptance.AuthStep.로그인_요청;
import static com.nexters.momo.acceptance.AuthStep.로그인_응답_실패_확인;
import static com.nexters.momo.acceptance.AuthStep.로그인_응답_확인;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_요청;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_응답_확인;

@DisplayName("인수 : 로그인")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.memberRepository.deleteAll();
    }

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
        사용자_가입_응답_확인(사용자_가입_응답, HttpStatus.CREATED, "유저 생성에 성공했습니다");
    }

    /**
     * Given 해당 서비스에 회원가입이 되어있지 않은 이메일이 있다.
     * When 잘못된 이메일을 통해 회원가입을 한다.
     * Then 사용자의 회원 가입이 실패한다.
     */
    @DisplayName("잘못된 사용자 이메일로 회원 가입 테스트")
    @Test
    void user_register_fail_because_invalid_email() {
        // given
        String invalidEmail = "@naver.com";
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(invalidEmail,
                "password", "Shine", 22, "developer", "uuid");

        // when
        var 사용자_가입_응답 = 사용자_가입_요청(memberRegisterRequest);

        // then
        사용자_가입_응답_확인(사용자_가입_응답, HttpStatus.BAD_REQUEST, "유저 생성에 실패했습니다");
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
        // given
        사용자_가입_요청(new MemberRegisterRequest("user@email.com",
                "password", "shine", 22, "developer", "device_uuid"));

        // when
        var 로그인_요청_응답 = 로그인_요청("user@email.com", "password", "device_uuid");

        // then
        로그인_응답_확인(로그인_요청_응답, HttpStatus.OK);
    }

    /**
     * Given 해당 이메일로 생성된 계정이 존재한다.
     * When 잘못된 비밀번호를 통해 로그인 한다.
     * Then 로그인 실패
     */
    @DisplayName("잘못된 비밀번호로 로그인을 시도한다")
    @Test
    void invalid_password_login_test() {
        // given
        사용자_가입_요청(new MemberRegisterRequest("user@email.com",
                "password", "shine", 22, "developer", "device_uuid"));

        // when
        var 로그인_요청_응답 = 로그인_요청("user@email.com", "invalid_password", "device_uuid");

        // then
        로그인_응답_실패_확인(로그인_요청_응답, HttpStatus.BAD_REQUEST, "Invalid Password");
    }

    /**
     * Given 해당 이메일로 생성된 계정이 존재한다.
     * When 잘못된 device Id 를 통해 로그인 한다.
     * Then 로그인 실패
     */
    @DisplayName("잘못된 device id로 로그인을 시도한다")
    @Test
    void invalid_device_id_login_test() {
        // given
        사용자_가입_요청(new MemberRegisterRequest("user@email.com",
                "password", "shine", 22, "developer", "device_uuid"));

        // when
        var 로그인_요청_응답 = 로그인_요청("user@email.com", "password", "invalid_device_uuid");

        // then
        로그인_응답_실패_확인(로그인_요청_응답, HttpStatus.BAD_REQUEST, "Invalid Device Id");
    }
}
