package com.nexters.momo.session.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidSessionIdException extends MomoRuntimeException {

    private static final String INVALID_SESSION_ID_ERROR_MESSAGE = "해당 세션 ID를 가지는 세션이 없습니다.";
    public InvalidSessionIdException() {
        super(INVALID_SESSION_ID_ERROR_MESSAGE);
    }
}
