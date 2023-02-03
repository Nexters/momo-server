package com.nexters.momo.common.response;

import org.springframework.http.HttpStatus;

public enum ResponseCodeAndMessages {

    /* MEMBER */
    MEMBER_CREATE_SUCCESS(HttpStatus.CREATED.value(), "유저 생성에 성공했습니다"),
    USER_LOGIN_SUCCESS(HttpStatus.OK.value(), "로그인에 성공했습니다.");

    private final int code;
    private final String message;

    ResponseCodeAndMessages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
