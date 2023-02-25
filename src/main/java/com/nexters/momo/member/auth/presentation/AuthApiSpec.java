package com.nexters.momo.member.auth.presentation;

import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 가입", description = "회원 가입 API 목록")
public interface AuthApiSpec {

    @Operation(summary = "유저 생성")
    ResponseEntity<Void> registerUser(MemberRegisterRequest memberRegisterRequest);
}
