package com.nexters.momo.team.domain;

import com.nexters.momo.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Embedded
    private TeamName teamName;

    @Column(name = "generation_id", nullable = false)
    private Long generationId;

    @ElementCollection
    @CollectionTable(name = "member", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "member_id")
    private Set<Long> members = new HashSet<>();

    public Team(String teamName, Long generationId) {
        this.teamName = new TeamName(teamName);
        this.generationId = generationId;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getAllMemberId() {
        return members.stream().collect(Collectors.toList());
    }

    public void addMember(Member member) {
        members.add(member.getId());
        member.addTeam(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
