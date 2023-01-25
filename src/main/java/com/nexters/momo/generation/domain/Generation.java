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
    private Long id;

    @Embedded
    private SignupCode signupCode;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static Generation of(
            Long id,
            SignupCode signupCode,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            LocalDateTime createdAt
    ) {
        Assert.notNull(signupCode, "signupCode must not be null");
        Assert.notNull(startedAt, "startedAt must not be null");
        Assert.notNull(endedAt, "endedAt must not be null");

        return new Generation(id, signupCode, startedAt, endedAt, createdAt);
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return this.startedAt.isBefore(now) && this.endedAt.isAfter(now);
    }

}
