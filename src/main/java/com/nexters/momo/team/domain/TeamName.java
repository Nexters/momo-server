package com.nexters.momo.team.domain;

import com.nexters.momo.team.exception.InvalidTeamNameException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamName {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 30;

    @Column(name = "team_name", length = 30, nullable = false)
    private String value;

    public TeamName(String value) {
        if (Objects.isNull(value) || !isValidTeamName(value)) {
            throw new InvalidTeamNameException();
        }
        this.value = value;
    }

    private boolean isValidTeamName(String name) {
        return name.length() >= MIN_LENGTH && name.length() <= MAX_LENGTH;
    }
}

