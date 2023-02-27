package com.nexters.momo.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MyPageStep {

    public static ExtractableResponse<Response> 베어러_인증으로_내_회원_정보_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static void 회원_정보_조회_확인(ExtractableResponse<Response> response, String email, String name) {
        assertAll(
                () -> assertThat(response.jsonPath().getBoolean("active")).isFalse(),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo(name),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email)
        );
    }
}
