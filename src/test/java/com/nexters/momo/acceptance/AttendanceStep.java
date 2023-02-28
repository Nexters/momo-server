package com.nexters.momo.acceptance;

import com.nexters.momo.attendance.presentation.dto.MemberAttendanceRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

public class AttendanceStep {

    public static ExtractableResponse<Response> 출석_체크_요청(String accessToken, Long sessionId, int attendanceCode) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MemberAttendanceRequest(sessionId, attendanceCode, LocalDateTime.now().plusHours(1L)))
                .when().post("/api/attendances")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }
}
