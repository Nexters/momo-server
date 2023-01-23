package com.nexters.momo.member.domain;

import com.nexters.momo.team.domain.Team;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update member set deleted = true where member_id = ?")
@Where(clause = "deleted = false")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "unique_id", length = 64, nullable = false)
    private String uniqueId;

    @Embedded
    private Password password;

    @Embedded
    private MemberName name;

    @Embedded
    private Phone phone;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "member_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    @Embedded
    private PolicyAgreed policyAgreed;

    @ElementCollection
    @CollectionTable(name = "team", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "team_id")
    private Set<Long> teams = new HashSet<>();

    public Member(String uniqueId, String password, String name, String phone, Role role, Boolean policyAgreed) {
        this.uniqueId = uniqueId;
        this.password = new Password(password);
        this.name = new MemberName(name);
        this.phone = new Phone(phone);
        this.role = role;
        this.policyAgreed = new PolicyAgreed(policyAgreed);
    }

    public boolean isSamePassword(String password) {
        return this.password.match(password);
    }

    public void changeStatus(MemberStatus status) {
        this.memberStatus = status;
    }

    public MemberStatus getStatus() {
        return this.memberStatus;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addTeam(Team team) {
        this.teams.add(team.getId());
    }
}
