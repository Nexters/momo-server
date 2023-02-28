package com.nexters.momo.session.presentation;

import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.session.application.dto.SessionDto;
import com.nexters.momo.session.presentation.dto.SessionAttendanceCodeResponse;
import com.nexters.momo.session.presentation.dto.SessionRequest;
import com.nexters.momo.session.presentation.dto.SessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "세션", description = "세션 API 목록")
public interface SessionApiSpec {

    @Operation(
            summary = "단일 세션 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema(implementation = SessionDto.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<SessionResponse> getSingleSession(Long id);

    @Operation(
            summary = "현재 기수의 모든 세션 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = SessionDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "현재 활성화된 기수가 없음",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))
                            )
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<List<SessionDto>> getAllSessions();

    @Operation(
            summary = "세션 생성",
            responses = {
                    @ApiResponse(responseCode = "201",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> createNewSession(SessionRequest request, List<MultipartFile> files);

    @Operation(
            summary = "세션 수정",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> updateSingleSession(Long id, @RequestBody SessionRequest request, List<MultipartFile> files);

    @Operation(
            summary = "세션 참여 코드 조회",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    public ResponseEntity<SessionAttendanceCodeResponse> getActiveSessionAttendanceCode(@PathVariable Long id);
}

