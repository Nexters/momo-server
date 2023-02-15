package com.nexters.momo.generation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupCode {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 12;

    @Column(name = "signup_code", nullable = false)
    private String value;

    // FIXME - validation 문구 추가
    public static SignupCode from(String value) {
        if (value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException((""));
        }

        return new SignupCode(value);
    }

}
