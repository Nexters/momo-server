package com.nexters.momo.session.controller;

import com.nexters.momo.common.response.BaseResponse;
import com.nexters.momo.session.dto.MultipleSessionResDto;
import com.nexters.momo.session.dto.PostSessionReqDto;
import com.nexters.momo.session.dto.SingleSessionResDto;
import com.nexters.momo.session.dto.UpdateSessionReqDto;
import com.nexters.momo.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static com.nexters.momo.common.response.ResponseCodeAndMessages.*;

/**
 * Session 관련 컨트롤러 클래스입니다.
 *
 * @author CHO Min Ho
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class SessionController {

    private final SessionService sessionService;

    /**
     * 단일 세션을 조회하는 API 입니다.
     * @param id 조회하려는 세션 ID
     * @return 조회한 세션
     */
    @GetMapping("/session/{id}")
    public BaseResponse<SingleSessionResDto> getSingleSession(@PathVariable("id") Long id) {
        return new BaseResponse<>(SESSION_SINGLE_READ_SUCCESS, sessionService.getSingleSession(id));
    }

    /**
     * 모든 세션을 조회하는 API 입니다.
     * @return 조회한 세션 리스트
     */
    @GetMapping("/session/all")
    public BaseResponse<List<MultipleSessionResDto>> getAllSessions() {
        return new BaseResponse<>(SESSION_LIST_READ_SUCCESS, sessionService.getSessionList());
    }

    /**
     * 세션을 생성하는 API 입니다.
     * @param dto 생성하려는 세션 정보
     * @return 생성된 세션의 ID
     */
    @PostMapping("/session/new")
    public BaseResponse<Long> createNewSession(@Valid PostSessionReqDto dto) {
        return new BaseResponse<>(SESSION_CREATE_SUCCESS, sessionService.createSession(dto));
    }

    /**
     * 단일 세션을 수정하는 API 입니다.
     * @param id 수정하려는 세션 ID
     * @param dto 수정하려는 세션 정보
     * @return 수정된 세션 ID
     */
    @PutMapping("session/{id}")
    public BaseResponse<Long> updateSingleSession(@PathVariable("id") Long id, @Valid UpdateSessionReqDto dto) {
        return new BaseResponse<>(SESSION_UPDATE_SUCCESS, sessionService.updateSession(dto));
    }
}