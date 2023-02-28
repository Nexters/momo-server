package com.nexters.momo.attendance.presentation;

import com.nexters.momo.attendance.presentation.dto.MemberAttendanceRequest;
import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.member.auth.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "참여", description = "참여 API 목록")
public interface AttendanceApiSpec {

    @Operation(
            summary = "참여 생성",
            responses = {
                    @ApiResponse(responseCode = "201",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> attendSession(@Parameter(description = "참여 요청 데이터", required = true) MemberAttendanceRequest request, @Parameter(hidden = true) Member member);
}
