package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthStep {

    public static void 사용자_가입_응답_확인(ExtractableResponse<Response> response, HttpStatus status) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("유저 생성에 성공했습니다")
        );
    }

    public static ExtractableResponse<Response> 사용자_가입_요청(MemberRegisterRequest memberRegisterRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRegisterRequest)
                .when().post("/api/auth/register")
                .then().log().all()
                .extract();
    }
}
