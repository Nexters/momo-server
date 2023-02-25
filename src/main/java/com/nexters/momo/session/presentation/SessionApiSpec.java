package com.nexters.momo.session.presentation;

import com.nexters.momo.session.application.dto.SessionDto;
import com.nexters.momo.session.presentation.dto.SessionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "세션", description = "세션 API 목록")
public interface SessionApiSpec {

    @Operation(summary = "단일 세션 조회")
    ResponseEntity<SessionDto> getSingleSession(Long id);

    @Operation(summary = "모든 세션 조회")
    ResponseEntity<List<SessionDto>> getAllSessions();

    @Operation(summary = "세션 생성")
    ResponseEntity<Long> createNewSession(SessionRequest request);

    @Operation(summary = "세션 수정")
    ResponseEntity<Long> updateSingleSession(Long id, SessionRequest request);
}

