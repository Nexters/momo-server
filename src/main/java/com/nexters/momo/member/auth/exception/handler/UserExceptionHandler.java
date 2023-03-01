package com.nexters.momo.member.auth.exception.handler;

import com.nexters.momo.common.response.ErrorCodeAndMessages;
import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.member.auth.exception.DuplicatedUserDeviceIdException;
import com.nexters.momo.member.auth.exception.DuplicatedUserEmailException;
import com.nexters.momo.member.auth.exception.InvalidUserEmailException;
import com.nexters.momo.member.auth.presentation.AuthController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AuthController.class)
public class UserExceptionHandler {

    @ExceptionHandler({InvalidUserEmailException.class, DuplicatedUserEmailException.class, DuplicatedUserDeviceIdException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> invalidUserRegisterExceptionHandler(Exception exception) {
        log.error("[InvalidUserRegisterException] Handler calling", exception);
        return ResponseEntity.status(ErrorCodeAndMessages.MEMBER_CREATE_FAIL.getCode())
                .body(ErrorResponse.from(ErrorCodeAndMessages.MEMBER_CREATE_FAIL));
    }
}
