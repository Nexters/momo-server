package com.nexters.momo.common.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int code;
    private final String message;

    public static ErrorResponse from(ResponseCodeAndMessages response) {
        return new ErrorResponse(response.getCode(), response.getMessage());
    }

    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(code, message);
    }

    private ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

