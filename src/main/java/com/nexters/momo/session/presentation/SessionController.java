package com.nexters.momo.session.presentation;

import com.nexters.momo.generation.application.GenerationService;
import com.nexters.momo.session.application.SessionImageService;
import com.nexters.momo.session.application.SessionService;
import com.nexters.momo.session.application.dto.SessionDto;
import com.nexters.momo.session.presentation.dto.SessionRequest;
import com.nexters.momo.session.presentation.dto.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * Session 관련 컨트롤러 클래스입니다.
 *
 * @author CHO Min Ho
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
public class SessionController implements SessionApiSpec {

    private final SessionService sessionService;

    private final SessionImageService sessionImageService;

    private final GenerationService generationService;

    /**
     * 단일 세션을 조회하는 API 입니다.
     *
     * @param id 조회하려는 세션 ID
     * @return 조회한 세션
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSingleSession(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toResponse(sessionService.getSingleSession(id),
                sessionImageService.getSessionImageList(id)));
    }

    @GetMapping("/active")
    public ResponseEntity<SessionDto> getActiveSession() {
        long currentGenerationId = generationService.getActiveGeneration().getId();
        return ResponseEntity.ok(sessionService.getActiveSession(currentGenerationId));
    }

    /**
     * 모든 세션을 조회하는 API 입니다.
     *
     * @return 조회한 세션 리스트
     */
    @GetMapping("/active/all")
    public ResponseEntity<List<SessionDto>> getAllSessions() {
        long currentGenerationId = generationService.getActiveGeneration().getId();
        return ResponseEntity.ok(sessionService.getSessionList(currentGenerationId));
    }

    /**
     * 세션을 생성하는 API 입니다.
     *
     * @param request 생성하려는 세션 정보
     * @param files   세션 상세 이미지 리스트
     * @return 생성된 세션의 ID
     */
    @PostMapping
    public ResponseEntity<Void> createNewSession(@RequestPart @Valid SessionRequest request,
                                                 @RequestPart List<MultipartFile> files) {
        SessionDto session = toDto(request);
        long currentGenerationId = generationService.getActiveGeneration().getId();
        sessionService.createSession(session, currentGenerationId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 단일 세션을 수정하는 API 입니다.
     *
     * @param id      수정하려는 세션 ID
     * @param request 수정하려는 세션 정보
     * @return 수정된 세션 ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSingleSession(@PathVariable("id") Long id,
                                                    @RequestPart @Valid SessionRequest request,
                                                    @RequestPart List<MultipartFile> files) {
        SessionDto session = toDto(request);
        sessionImageService.deleteSessionImage(id);
        sessionImageService.createSessionImage(id, files);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable("id") Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    private SessionDto toDto(SessionRequest request) {
        return SessionDto.of(
                request.getId(),
                request.getTitle(),
                request.getWeek(),
                request.getContent(),
                request.getStartAt(),
                request.getEndAt(),
                request.getAddress(),
                request.getAddressDetail(),
                request.getPoint(),
                request.getAttendanceClosedAt(),
                request.getAttendanceClosedAt()
        );
    }

    private SessionResponse toResponse(SessionDto dto, List<String> files) {
        return new SessionResponse(dto, files);
    }
}
