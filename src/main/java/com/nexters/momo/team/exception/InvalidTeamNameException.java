package com.nexters.momo.team.exception;

import com.nexters.momo.common.exception.MomoRuntimeException;

public class InvalidTeamNameException extends MomoRuntimeException {

    private static final String INVALID_LENGTH_TEAM_NAME_ERROR_MESSAGE = "잘못된 길이의 팀 이름 입니다.";

    public InvalidTeamNameException() {
        super(INVALID_LENGTH_TEAM_NAME_ERROR_MESSAGE);
    }
}
