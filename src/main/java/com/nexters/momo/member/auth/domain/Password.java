package com.nexters.momo.member.auth.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        this.value = value;
    }

    public boolean match(String password) {
        return this.value.equals(password);
    }
}
