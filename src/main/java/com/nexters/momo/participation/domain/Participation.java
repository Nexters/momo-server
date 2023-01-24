package com.nexters.momo.participation.domain;

import com.nexters.momo.generation.domain.Generation;
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

    // TODO - Add user

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    enum Position {
        DESIGNER,
        DEVELOPER
    }

    public static Participation of(
            Long id,
            Generation generation,
            Position position
    ) {
        Assert.notNull(generation, "generation must not be null");
        Assert.notNull(generation, "position must not be null");

        return new Participation(id, generation.getId(), position);
    }
}
