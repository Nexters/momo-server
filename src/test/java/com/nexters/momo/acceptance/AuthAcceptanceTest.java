package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static com.nexters.momo.acceptance.AuthStep.사용자_가입_요청;
import static com.nexters.momo.acceptance.AuthStep.사용자_가입_응답_확인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        Map<String, String> params = new HashMap<>();
        params.put("email", USER_EMAIL);
        params.put("password", USER_PASSWORD);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.accessToken")).isNotBlank(),
                () -> assertThat(response.jsonPath().getString("data.refreshToken")).isNotBlank()
        );
    }
}
