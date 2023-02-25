package com.nexters.momo.member.mypage.common.dto.response;

import com.nexters.momo.member.auth.domain.Occupation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoDto {

    private boolean active;

    private String name;

    private String email;

    private Occupation occupation;

    @QueryProjection
    public MemberInfoDto(boolean active, String name, String email, Occupation occupation) {
        this.active = active;
        this.name = name;
        this.email = email;
        this.occupation = occupation;
    }
}
