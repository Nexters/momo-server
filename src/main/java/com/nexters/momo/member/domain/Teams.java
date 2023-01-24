package com.nexters.momo.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teams {

    @ElementCollection
    @CollectionTable(name = "team", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "team_id")
    private Set<Long> teams = new HashSet<>();

    public void add(Long teamId) {
        this.teams.add(teamId);
    }

    public List<Long> getAllTeamId() {
        return List.copyOf(teams);
    }

    public boolean contains(Long teamId) {
        return this.teams.contains(teamId);
    }
}
