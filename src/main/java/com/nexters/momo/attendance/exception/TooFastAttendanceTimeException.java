package com.nexters.momo.attendance.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class TooFastAttendanceTimeException extends MomoRuntimeException {

    private static final String TOO_FAST_ATTENDANCE_ERROR_MESSAGE = "출석 체크는 세션 시작 30분 전부터 가능합니다.";
    public TooFastAttendanceTimeException() {
        super(TOO_FAST_ATTENDANCE_ERROR_MESSAGE);
    }
}
