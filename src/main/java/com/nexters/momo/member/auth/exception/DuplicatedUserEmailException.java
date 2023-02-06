package com.nexters.momo.member.auth.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class DuplicatedUserEmailException extends MomoRuntimeException {

    private static final String EXCEPTION_MESSAGE = "이미 가입된 유저 이메일 입니다";

    public DuplicatedUserEmailException() {
        super(EXCEPTION_MESSAGE);
    }
}
