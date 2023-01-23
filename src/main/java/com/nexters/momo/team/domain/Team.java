package com.nexters.momo.team.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "generation_id", nullable = false)
    private Long generationId;

    @ElementCollection
    @CollectionTable(name = "member", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "member_id")
    private Set<Long> members = new HashSet<>();

    public Team(String teamName, Long generationId) {
        this.teamName = teamName;
        this.generationId = generationId;
    }
}
