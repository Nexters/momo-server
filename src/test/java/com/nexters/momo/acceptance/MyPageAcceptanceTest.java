package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nexters.momo.acceptance.AuthStep.로그인_되어_있음;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_요청;
import static com.nexters.momo.acceptance.MyPageStep.베어러_인증으로_내_회원_정보_조회_요청;
import static com.nexters.momo.acceptance.MyPageStep.회원_정보_조회_확인;

@DisplayName("인수 : 사용자 정보")
public class MyPageAcceptanceTest extends RandomPortConfigure {

    @DisplayName("사용자 본인의 프로필을 조회한다.")
    @Test
    public void find_member_info() {
        // given
        사용자_가입_요청(new MemberRegisterRequest("user@email.com",
                "password", "shine", 22, "developer", "device_uuid"));

        // when
        String accessToken = 로그인_되어_있음("user@email.com", "password", "device_uuid");

        // given
        var 회원_정보_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);

        // then
        회원_정보_조회_확인(회원_정보_응답, "user@email.com", "shine");
    }
}
