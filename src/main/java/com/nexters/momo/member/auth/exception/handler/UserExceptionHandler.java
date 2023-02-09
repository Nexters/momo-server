package com.nexters.momo.member.auth.exception.handler;

import com.nexters.momo.common.response.BaseResponse;
import com.nexters.momo.common.response.ResponseCodeAndMessages;
import com.nexters.momo.member.auth.exception.DuplicatedUserDeviceIdException;
import com.nexters.momo.member.auth.exception.DuplicatedUserEmailException;
import com.nexters.momo.member.auth.exception.InvalidUserEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({InvalidUserEmailException.class, DuplicatedUserEmailException.class, DuplicatedUserDeviceIdException.class})
    public ResponseEntity<BaseResponse<Void>> invalidUserRegisterExceptionHandler(Exception exception) {
        log.info("[InvalidUserRegisterException] Handler calling");
        return new ResponseEntity<>(new BaseResponse<>(ResponseCodeAndMessages.MEMBER_CREATE_FAIL), HttpStatus.BAD_REQUEST);
    }
}
