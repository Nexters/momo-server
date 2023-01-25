package com.nexters.momo.attendance.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidAttendanceCodeException extends MomoRuntimeException {

    private static final String INVALID_ATTENDANCE_CODE_ERROR_MESSAGE = "출석 코드가 일치하지 않습니다.";
    public InvalidAttendanceCodeException() {
        super(INVALID_ATTENDANCE_CODE_ERROR_MESSAGE);
    }
}
