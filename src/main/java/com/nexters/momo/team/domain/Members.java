package com.nexters.momo.team.domain;

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
public class Members {

    @ElementCollection
    @CollectionTable(name = "member", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "member_id")
    private Set<Long> members = new HashSet<>();

    public void add(Long memberId) {
        this.members.add(memberId);
    }

    public List<Long> getAllMemberId() {
        return List.copyOf(members);
    }

    public void deleteById(Long memberId) {
        this.members.remove(memberId);
    }

    public boolean contains(Long memberId) {
        return this.members.contains(memberId);
    }
}
