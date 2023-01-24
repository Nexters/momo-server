package com.nexters.momo.session.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidSessionTimeException extends MomoRuntimeException {

    private static final String INVALID_SESSION_TIME_ERROR_MESSAGE = "세션 종료 시각은 세션 시작 시각보다 뒤여야 합니다.";
    public InvalidSessionTimeException() {
        super(INVALID_SESSION_TIME_ERROR_MESSAGE);
    }
}
