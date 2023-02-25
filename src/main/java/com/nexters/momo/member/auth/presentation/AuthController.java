package com.nexters.momo.member.auth.presentation;

import com.nexters.momo.member.auth.application.MemberService;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApiSpec {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberService.register(memberRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
