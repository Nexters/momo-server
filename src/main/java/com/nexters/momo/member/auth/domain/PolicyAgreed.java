package com.nexters.momo.member.auth.domain;

import com.nexters.momo.member.exception.UserNotAgreePolicyException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyAgreed {

    @Column(name = "policy_agreed", nullable = false)
    private boolean value;

    public PolicyAgreed(boolean value) {
        if (!isUserAgreePolicy(value)) {
            throw new UserNotAgreePolicyException();
        }
        this.value = value;
    }

    private boolean isUserAgreePolicy(Boolean value) {
        return value == true;
    }
}
