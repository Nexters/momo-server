package com.nexters.momo.member.domain;

import com.nexters.momo.team.domain.Team;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update member set deleted = true where member_id = ?")
@Where(clause = "deleted = false")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private MemberName name;

    @Column(name = "device_unique_id")
    private String deviceUniqueId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Occupation occupation;

    @Column(name = "member_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    @Embedded
    private PolicyAgreed policyAgreed;

    @Embedded
    private Teams teams = new Teams();

    private boolean deleted = false;

    public Member(String email, String password, String name, String deviceUniqueId, Role role, Occupation occupation, Boolean policyAgreed) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new MemberName(name);
        this.deviceUniqueId = deviceUniqueId;
        this.role = role;
        this.occupation = occupation;
        this.policyAgreed = new PolicyAgreed(policyAgreed);
    }

    public boolean isSamePassword(String password) {
        return this.password.match(password);
    }

    public boolean isAlreadyJoinTeam(Team team) {
        return this.teams.contains(team.getId());
    }

    public void changeStatus(MemberStatus status) {
        this.memberStatus = status;
    }

    public void addTeam(Team team) {
        this.teams.add(team.getId());
        if(!team.isRegisteredMember(this)) {
            team.addMember(this);
        }
    }

    public boolean isDeleted() {
        return deleted;
    }

    public MemberStatus getStatus() {
        return this.memberStatus;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getAllTeamId() {
        return this.teams.getAllTeamId();
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
}
