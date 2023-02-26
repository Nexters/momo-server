package com.nexters.momo.member.auth.presentation;

import com.nexters.momo.generation.application.GenerationService;
import com.nexters.momo.member.auth.application.MemberService;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApiSpec {

    private final MemberService memberService;

    private final GenerationService generationService;

    @GetMapping
    public ResponseEntity<Void> getAccess(@AuthenticationPrincipal Member member) {
        if (canAccess(member)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private boolean canAccess(Member member) {
        return member.isActive() && generationService.isPresentActive();
    }

    @GetMapping("/signup-code")
    public ResponseEntity<Void> validSignupCode(@RequestParam String code) {
        generationService.validGenerationCode(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberService.register(memberRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
