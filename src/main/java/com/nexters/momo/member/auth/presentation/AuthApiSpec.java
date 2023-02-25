package com.nexters.momo.member.auth.presentation;

import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.presentation.dto.MemberRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 가입", description = "회원 가입 API 목록")
public interface AuthApiSpec {

    @Operation(
            summary = "세션 페이지 접근 전 유저 권한 체크",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "403",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> getAccess(@Parameter(hidden = true) Member member);


    @Operation(
            summary = "유저 생성",
            responses = {
                    @ApiResponse(responseCode = "201",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> registerUser(@RequestBody MemberRegisterRequest memberRegisterRequest);
}
