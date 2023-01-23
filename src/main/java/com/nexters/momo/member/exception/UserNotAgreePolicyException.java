package com.nexters.momo.member.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class UserNotAgreePolicyException extends MomoRuntimeException {

    private static final String POLICY_NOT_AGREE_ERROR_MESSAGE = "개인정보 동의를 한 사용자만 활동이 가능합니다.";


    public UserNotAgreePolicyException() {
        super(POLICY_NOT_AGREE_ERROR_MESSAGE);
    }
}
