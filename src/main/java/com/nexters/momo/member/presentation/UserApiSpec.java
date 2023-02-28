package com.nexters.momo.member.presentation;

import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.dto.MemberLookUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "유저", description = "유저 API 목록")
public interface UserApiSpec {

    @Operation(
            summary = "유저 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema(implementation = MemberLookUpResponse.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<MemberLookUpResponse> searchMe(@Parameter(hidden = true) Member member);
}
