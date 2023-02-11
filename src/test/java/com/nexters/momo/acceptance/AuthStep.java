package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthStep {

    public static void 사용자_가입_응답_확인(ExtractableResponse<Response> response, HttpStatus status, String message) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(message)
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

    public static void 로그인_응답_확인(ExtractableResponse<Response> response, HttpStatus status) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.accessToken")).isNotBlank(),
                () -> assertThat(response.jsonPath().getString("data.refreshToken")).isNotBlank()
        );
    }

    public static ExtractableResponse<Response> 로그인_요청(String email, String password, String uuid) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("uuid", uuid);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/auth/login")
                .then().log().all()
                .extract();
    }

    public static String 로그인_되어_있음(String email, String password, String uuid) {
        ExtractableResponse<Response> response = 로그인_요청(email, password, uuid);
        return response.jsonPath().getString("data.accessToken");
    }

    public static ExtractableResponse<Response> 로그아웃_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/auth/logout")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static void 로그아웃_응답_확인(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getInt("code")).isEqualTo(200);
    }

    public static void 로그인_응답_실패_확인(ExtractableResponse<Response> response, HttpStatus status, String message) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo(message),
                () -> assertThat(response.jsonPath().getString("data")).isNull()
        );
    }
}
