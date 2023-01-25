package com.nexters.momo.member.domain;

import com.nexters.momo.member.exception.InvalidUserPhoneException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

    private static final int MIN_LENGTH = 11;
    private static final int MAX_LENGTH = 13;

    @Column(name = "phone", length = 13)
    private String value;

    public Phone(String value) {
        if (Objects.isNull(value) || !isValidPhoneLength(value)) {
            throw new InvalidUserPhoneException();
        }
        this.value = value;
    }

    private boolean isValidPhoneLength(String value) {
        return value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
    }
}
