package com.nexters.momo.generation.presentation;

import com.nexters.momo.generation.presentation.dto.GenerationRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GenerationStep {

    public static void 기수_생성_확인(ExtractableResponse<Response> response, HttpStatus status) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(status.value())
        );
    }

    public static ExtractableResponse<Response> 기수_생성_요청(GenerationRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/generations")
                .then()
                .log().all()
                .extract();
    }

    public static void 기수_비활성화_확인(ExtractableResponse<Response> response, HttpStatus status) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(status.value()),
                () -> assertThat(response.jsonPath().getInt("code")).isEqualTo(status.value())
        );
    }

    public static ExtractableResponse<Response> 기수_비활성화_요청(Long id) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", id)
                .when()
                .patch("/api/generations/{id}")
                .then()
                .log().all()
                .extract();
    }

}
