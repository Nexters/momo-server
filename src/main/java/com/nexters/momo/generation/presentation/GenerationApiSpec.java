package com.nexters.momo.generation.presentation;

import com.nexters.momo.generation.presentation.dto.GenerationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "기수", description = "기수 API 목록")
public interface GenerationApiSpec {

    @Operation(summary = "기수 생성")
    ResponseEntity<Void> create(GenerationRequest request);

    @Operation(summary = "현재 기수 비활성화")
    ResponseEntity<Void> deactivate(Long id);
}
