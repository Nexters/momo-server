package com.nexters.momo.member.auth.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidUserNameException extends MomoRuntimeException {

    private static final String INVALID_LENGTH_NAME_ERROR_MESSAGE = "잘못된 길이의 사용자 이름 입니다.";

    public InvalidUserNameException() {
        super(INVALID_LENGTH_NAME_ERROR_MESSAGE);
    }
}
