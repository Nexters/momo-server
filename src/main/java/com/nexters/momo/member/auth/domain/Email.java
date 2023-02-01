package com.nexters.momo.member.auth.domain;

import com.nexters.momo.member.auth.exception.InvalidUserEmailException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    /**
     * Regular Expression by RFC 5322 for Email Validation
     * If implemented correctly, the RFC 5322-compliant Regular Expression should validate 99.99% of the valid email addresses.
     * @link https://datatracker.ietf.org/doc/html/rfc5322#section-3.4.1
     * @author jiwoo
     */
    private static final String regexPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Column(name = "email", nullable = false)
    private String value;

    public Email(String email) {
        if (!this.isValid(email)) {
            throw new InvalidUserEmailException();
        }
        this.value = email;
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String email) {
        return this.patternMatches(email, regexPattern);
    }

    private boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
