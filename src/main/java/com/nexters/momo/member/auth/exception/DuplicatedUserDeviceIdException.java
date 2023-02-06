package com.nexters.momo.member.auth.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class DuplicatedUserDeviceIdException extends MomoRuntimeException {

    private static final String EXCEPTION_MESSAGE = "이미 가입된 유저 기기 입니다";

    public DuplicatedUserDeviceIdException() {
        super(EXCEPTION_MESSAGE);
    }
}
