package com.nexters.momo.member.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidUserPhoneException extends MomoRuntimeException {

    private static final String INVALID_LENGTH_PHONE_ERROR_MESSAGE = "잘못된 길이의 사용자 전화번호 입니다.";


    public InvalidUserPhoneException() {
        super(INVALID_LENGTH_PHONE_ERROR_MESSAGE);
    }
}
