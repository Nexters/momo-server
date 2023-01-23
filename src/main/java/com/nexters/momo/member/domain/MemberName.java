package com.nexters.momo.member.domain;

import com.nexters.momo.member.exception.InvalidUserNameException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberName {

    private static final int MIN_LENGTH = 0;
    private static final int MAX_LENGTH = 20;

    @Column(name = "member_name", length = 20, nullable = false)
    private String value;

    public MemberName(String value) {
        if (Objects.isNull(value) || !isValidName(value)) {
            throw new InvalidUserNameException();
        }
        this.value = value;
    }

    private boolean isValidName(String name) {
        return name.length() > MIN_LENGTH && name.length() < MAX_LENGTH;
    }
}

