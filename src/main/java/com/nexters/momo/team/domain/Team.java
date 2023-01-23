package com.nexters.momo.team.domain;

import com.nexters.momo.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

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

    @Embedded
    private Members members = new Members();

    public Team(String teamName, Long generationId) {
        this.teamName = new TeamName(teamName);
        this.generationId = generationId;
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName.getValue();
    }

    public Long getGenerationId() {
        return generationId;
    }

    public List<Long> getAllMemberId() {
        return members.getAllMemberId();
    }

    public void addMember(Member member) {
        this.members.add(member.getId());
    }

    public void deleteMember(Member member) {
        this.members.deleteById(member.getId());
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
