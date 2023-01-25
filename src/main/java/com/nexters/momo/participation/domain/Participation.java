package com.nexters.momo.participation.domain;

import com.nexters.momo.generation.domain.Generation;
import com.nexters.momo.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long generationId;

    @Column(nullable = false)
    private Long memberId;


    public static Participation of(
            Long id,
            Generation generation,
            Member member
    ) {
        Assert.notNull(generation, "generation must not be null");
        Assert.notNull(member, "meember must not be null");

        return new Participation(id, generation.getId(), member.getId());
    }
}
