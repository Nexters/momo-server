package com.nexters.momo.common.response;

import org.springframework.http.HttpStatus;

public enum ErrorCodeAndMessages {

    /* MEMBER */
    MEMBER_CREATE_FAIL(HttpStatus.BAD_REQUEST, "유저 생성에 실패했습니다"),
    MEMBER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 유저 입니다"),
    MEMBER_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "사용자 토큰이 토큰이 만료 되었습니다"),

    /* SIGNUP */
    INVALID_GENERATION_CODE(HttpStatus.BAD_REQUEST, "보안코드를 확인해주세요")
    ;

    private final HttpStatus code;
    private final String message;

    ErrorCodeAndMessages(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
