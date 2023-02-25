package com.nexters.momo.member.presentation;

import com.nexters.momo.member.auth.application.MemberService;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.dto.MemberLookUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController implements UserApiSpec {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberLookUpResponse> searchMe(@AuthenticationPrincipal Member member) {
        MemberLookUpResponse memberLookUpResponse = memberService.findUserWithDetailInfo(member);
        return ResponseEntity.ok().body(memberLookUpResponse);
    }
}
