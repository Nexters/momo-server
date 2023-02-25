package com.nexters.momo.member.mypage.presentation;

import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.mypage.common.dto.response.MemberLookUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "유저", description = "유저 API 목록")
public interface UserApiSpec {

    @Operation(summary = "유저 조회")
    ResponseEntity<MemberLookUpResponse> searchMe(Member member);
}
