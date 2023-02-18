package com.nexters.momo.member.auth.exception.handler;

import com.nexters.momo.common.response.ErrorResponse;
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
    public ResponseEntity<ErrorResponse> invalidUserRegisterExceptionHandler(Exception exception) {
        log.error("[InvalidUserRegisterException] Handler calling", exception);
        return new ResponseEntity<>(ErrorResponse.from(ResponseCodeAndMessages.MEMBER_CREATE_FAIL), HttpStatus.BAD_REQUEST);
    }
}
