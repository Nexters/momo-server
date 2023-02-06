package com.nexters.momo.member.auth.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class UserNotFoundException extends MomoRuntimeException {

    private static final String EXCEPTION_MESSAGE = "해당 유저를 찾을 수 없습니다";

    public UserNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
