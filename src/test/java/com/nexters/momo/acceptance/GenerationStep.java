package com.nexters.momo.acceptance;

import com.nexters.momo.generation.presentation.dto.GenerationRequest;
import io.restassured.RestAssured;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class GenerationStep {

    public static void 기수_생성_요청(String accessToken, String signupCode) {
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new GenerationRequest(50, signupCode))
                .when().post("/api/generations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
