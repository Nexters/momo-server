package com.nexters.momo.member.auth.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update member set deleted = true where member_id = ?")
@Where(clause = "deleted = false")
public class Member implements UserDetails {

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "member_roles",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Authority> memberRoles = new HashSet<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Occupation occupation;

    @Column(nullable = false)
    private boolean active;

    @Embedded
    private PolicyAgreed policyAgreed;

    private boolean deleted = false;

    public Member(String email, String password, String name, String deviceUniqueId, Role role, Occupation occupation, Boolean policyAgreed) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.name = new MemberName(name);
        this.deviceUniqueId = deviceUniqueId;
        this.memberRoles.add(new Authority(role));
        this.occupation = occupation;
        this.active = false;
        this.policyAgreed = new PolicyAgreed(policyAgreed);
    }

    public boolean isSamePassword(String password) {
        return this.password.match(password);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }

    public void changeActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public void addRole(Role role) {
        memberRoles.add(new Authority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(memberRoles);
    }

    @Override
    public String getPassword() {
        return this.password.getValue();
    }

    @Override
    public String getUsername() {
        return this.email.getValue();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
