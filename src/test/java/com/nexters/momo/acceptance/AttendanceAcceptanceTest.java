package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.domain.MemberRepository;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import com.nexters.momo.support.RandomPortConfigure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.nexters.momo.acceptance.AttendanceStep.출석_체크_요청;
import static com.nexters.momo.acceptance.AuthStep.로그인_되어_있음;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_요청;
import static com.nexters.momo.acceptance.GenerationStep.기수_생성_요청;
import static com.nexters.momo.acceptance.SessionStep.파일_전송과_함께_세션_생성_요청;
import static com.nexters.momo.acceptance.SessionStep.활성화된_세션_조회_요청;
import static com.nexters.momo.acceptance.SessionStep.활성회된_세션_인증코드_조회;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("인수 : 참여")
public class AttendanceAcceptanceTest extends RandomPortConfigure {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        this.memberRepository.deleteAll();
    }

    /**
     * Given 이전에 가입한 사용자가 있으며, 로그인 되어 있다
     * And 기수가 생성되어 있다
     * And 세션이 생성되어 있다
     * And 세션을 조회한후
     * When 출석체크를 누르면
     * Then 정상적으로 출석체크 된다
     */
    @DisplayName("세션에 참여한다")
    @Test
    public void session_attendance_test() throws IOException {
        // given
        사용자_가입_요청(new MemberRegisterRequest("shine@naver.com",
                "password", "Shine", 22, "developer", "uuid"));

        // and
        var accessToken = 로그인_되어_있음("shine@naver.com", "password", "uuid");

        // and
        기수_생성_요청(accessToken);

        // and
        파일_전송과_함께_세션_생성_요청(accessToken);

        // and
        var 활성화된_세션_아이디 = 활성화된_세션_조회_요청(accessToken);

        // and
        var 세션_인증코드 = 활성회된_세션_인증코드_조회(accessToken, 활성화된_세션_아이디);

        // when
        var 출석_체크_요청_응답 = 출석_체크_요청(accessToken, 활성화된_세션_아이디, 세션_인증코드);

        // then
        assertThat(출석_체크_요청_응답.statusCode()).isEqualTo(201);
    }
}
