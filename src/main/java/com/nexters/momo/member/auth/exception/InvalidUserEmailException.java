package com.nexters.momo.member.auth.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidUserEmailException extends MomoRuntimeException {

    private static final String INVALID_EMAIL_ERROR_MESSAGE = "잘못된 형식의 이메일 입니다.";

    public InvalidUserEmailException() {
        super(INVALID_EMAIL_ERROR_MESSAGE);
    }
}
