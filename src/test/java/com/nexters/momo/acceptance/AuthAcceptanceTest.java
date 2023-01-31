package com.nexters.momo.acceptance;

import com.nexters.momo.member.auth.presentation.dto.UserRegisterRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("test@email.com",
                "password", "Shine", 22, "developer", "device_uuid");

        // when
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRegisterRequest)
                .when().post("/api/auth/register")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo(201),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("유저 생성에 성공했습니다")
        );
    }
}
