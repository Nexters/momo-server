package com.nexters.momo.member.auth.presentation;

import com.nexters.momo.common.response.BaseResponse;
import com.nexters.momo.member.auth.business.MemberDetailsService;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.nexters.momo.common.response.ResponseCodeAndMessages.MEMBER_CREATE_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberDetailsService memberDetailsService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> registerUser(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberDetailsService.register(memberRegisterRequest);
        return new ResponseEntity<>(new BaseResponse<>(MEMBER_CREATE_SUCCESS), HttpStatus.CREATED);
    }
}
