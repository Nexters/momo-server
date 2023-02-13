package com.nexters.momo.common.response;

import org.springframework.http.HttpStatus;

public enum ResponseCodeAndMessages {

    /* MEMBER */
    MEMBER_CREATE_SUCCESS(HttpStatus.CREATED.value(), "유저 생성에 성공했습니다"),
    MEMBER_CREATE_FAIL(HttpStatus.BAD_REQUEST.value(), "유저 생성에 실패했습니다"),
    MEMBER_LOGIN_SUCCESS(HttpStatus.OK.value(), "로그인에 성공했습니다."),
    MEMBER_LOGOUT_SUCCESS(HttpStatus.OK.value(), "로그아웃에 성공했습니다."),
    MEMBER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 유저 입니다"),

    /* SESSION */
    SESSION_SINGLE_READ_SUCCESS(HttpStatus.OK.value(), "단일 세션 조회에 성공했습니다."),
    SESSION_CREATE_SUCCESS(HttpStatus.CREATED.value(), "세션 생성에 성공했습니다."),
    SESSION_UPDATE_SUCCESS(HttpStatus.OK.value(), "세션 수정에 성공했습니다."),
    SESSION_LIST_READ_SUCCESS(HttpStatus.OK.value(), "세션 리스트 조회에 성공했습니다."),
    SESSION_DELETE_SUCCESS(HttpStatus.OK.value(), "세션 삭제에 성공했습니다");

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
