package com.nexters.momo.acceptance;

import com.nexters.momo.session.domain.Point;
import com.nexters.momo.session.presentation.dto.SessionRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SessionStep {

    public static void 세션_생성_요청(String accessToken, File file) {
        LocalDateTime now = LocalDateTime.now();

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("request", new SessionRequest(1L, "2주차", 2, "2주차 contents", now, now.plusMinutes(30L), "address", "address_detail", Point.of(12.123456, 123.123), now, now.plusMinutes(30L)), MediaType.APPLICATION_JSON_VALUE)
                .multiPart("files", file)
                .when().post("/api/sessions")
                .then().log().all()
                .extract();
    }

    public static void 파일_전송과_함께_세션_생성_요청(String accessToken) throws IOException {
        File tempFile = File.createTempFile("temp_", ".jpg");
        세션_생성_요청(accessToken, tempFile);
        tempFile.deleteOnExit();
    }

    public static Long 활성화된_세션_조회_요청(String accessToken) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/sessions/active")
                .then().log().all()
                .extract();

        return extract.jsonPath().getLong("id");
    }

    public static Integer 활성회된_세션_인증코드_조회(String accessToken, Long 활성화된_세션_아이디) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/sessions/{id}/code", 활성화된_세션_아이디)
                .then().log().all()
                .extract();

        return response.jsonPath().getInt("attendanceCode");
    }
}
