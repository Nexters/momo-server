package com.nexters.momo.session.controller;

import com.nexters.momo.session.dto.SessionDto;
import com.nexters.momo.session.dto.SessionReq;
import com.nexters.momo.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Session 관련 컨트롤러 클래스입니다.
 *
 * @author CHO Min Ho
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
@Slf4j
public class SessionController {

    private final SessionService sessionService;

    /**
     * 단일 세션을 조회하는 API 입니다.
     * @param id 조회하려는 세션 ID
     * @return 조회한 세션
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSingleSession(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sessionService.getSingleSession(id));
    }

    /**
     * 모든 세션을 조회하는 API 입니다.
     * @return 조회한 세션 리스트
     *
     * TODO : generation service 의존성 주입 및 현재 기수를 반환하는 메서드를 getSessionList 메서드의 파라미터로 삽입
     */
    @GetMapping
    public ResponseEntity<List<SessionDto>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getSessionList(1L));
    }

    /**
     * 세션을 생성하는 API 입니다.
     * @param req 생성하려는 세션 정보
     * @return 생성된 세션의 ID
     *
     * TODO : Generation Service 의존성 주입 및 현재 기수를 반환하는 메서드를 createSession 의 두번째 인자로 삽입
     */
    @PostMapping
    public ResponseEntity<Long> createNewSession(@RequestBody @Valid SessionReq req) {
        Long id = sessionService.createSession(req.getSession(), 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    /**
     * 단일 세션을 수정하는 API 입니다.
     * @param id 수정하려는 세션 ID
     * @param req 수정하려는 세션 정보
     * @return 수정된 세션 ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSingleSession(@PathVariable("id") Long id, @RequestBody @Valid SessionReq req) {
        return ResponseEntity.ok(sessionService.updateSession(id, req.getSession()));
    }
}
