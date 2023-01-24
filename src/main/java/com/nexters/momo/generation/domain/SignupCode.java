package com.nexters.momo.generation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupCode {

    @Column(name = "signup_code", nullable = false)
    private String value;

    public static SignupCode from(String value) {
        Assert.notNull(value, "signupCode value must not be null");

        return new SignupCode(value);
    }

}
