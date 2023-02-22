package com.nexters.momo.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final HttpStatus code;
    private final String message;

    public static ErrorResponse from(ErrorCodeAndMessages response) {
        return new ErrorResponse(response.getCode(), response.getMessage());
    }

    public static ErrorResponse of(HttpStatus code, String message) {
        return new ErrorResponse(code, message);
    }

    private ErrorResponse(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}

