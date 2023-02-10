package com.nexters.momo.generation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Generation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long generationId;

    @Column(nullable = false)
    private int number;

    @Embedded
    private SignupCode signupCode;

    @Column(nullable = false)
    private boolean active;

    public static Generation of(
            Long id,
            int number,
            SignupCode signupCode,
            boolean active
    ) {
        Assert.notNull(signupCode, "signupCode must not be null");

        return new Generation(id, number, signupCode, active);
    }

    public boolean isActive() {
        return active;
    }

}
