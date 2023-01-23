package com.nexters.momo.session.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 세션의 status 를 표현하기 위한 enum 클래스입니다.
 *
 * @author CHO Min Ho
 */
public enum SessionStatus {
    BEFORE("before"),
    PROCEEDING("proceeding"),
    AFTER("after");

    private final String value;

    SessionStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static SessionStatus from(String value) {
        for (SessionStatus status : SessionStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
