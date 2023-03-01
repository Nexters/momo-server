package com.nexters.momo.acceptance;

import com.nexters.momo.generation.domain.GenerationRepository;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import com.nexters.momo.support.TestSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.nexters.momo.acceptance.AuthStep.로그인_되어_있음;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_요청;
import static com.nexters.momo.acceptance.GenerationStep.기수_생성_요청;
import static com.nexters.momo.acceptance.MyPageStep.베어러_인증으로_내_회원_정보_조회_요청;
import static com.nexters.momo.acceptance.MyPageStep.회원_정보_조회_확인;

@DisplayName("인수 : 사용자 정보")
public class MyPageAcceptanceTest extends TestSetup {

    @Autowired
    GenerationRepository generationRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // admin 로그인
        var adminAccessToken = 로그인_되어_있음("admin@naver.com", "admin_password", "admin_uuid");

        // admin의 기수 생성
        기수_생성_요청(adminAccessToken, SIGNUP_CODE);
    }

    @DisplayName("사용자 본인의 프로필을 조회한다.")
    @Test
    public void find_member_info() {
        // given
        사용자_가입_요청(new MemberRegisterRequest("user@email.com",
                "password", "shine", 22, "developer", "device_uuid", SIGNUP_CODE));

        // when
        String accessToken = 로그인_되어_있음("user@email.com", "password", "device_uuid");

        // given
        var 회원_정보_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);

        // then
        회원_정보_조회_확인(회원_정보_응답, "user@email.com", "shine");
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
        this.generationRepository.deleteAll();
    }
}
