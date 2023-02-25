package com.nexters.momo.generation.presentation;

import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.generation.presentation.dto.GenerationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "기수", description = "기수 API 목록")
public interface GenerationApiSpec {

    @Operation(
            summary = "기수 생성",
            responses = {
                    @ApiResponse(responseCode = "201",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> create(@RequestBody GenerationRequest request);

    @Operation(
            summary = "현재 기수 비활성화",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
            }
    )
    ResponseEntity<Void> deactivate(Long id);
}
