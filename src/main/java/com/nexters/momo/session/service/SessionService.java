package com.nexters.momo.session.service;

import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.dto.MultipleSessionResDto;
import com.nexters.momo.session.dto.PostSessionReqDto;
import com.nexters.momo.session.dto.SingleSessionResDto;
import com.nexters.momo.session.dto.UpdateSessionReqDto;
import com.nexters.momo.session.exception.InvalidSessionIdException;
import com.nexters.momo.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Session 관련 서비스 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;

    /**
     * Session 을 생성하는 메서드입니다.
     * @param dto 생성할 Session의 정보
     * @return 생성된 Session 의 ID
     */
    public Long createSession(PostSessionReqDto dto) {
        return sessionRepository.
                save(Session.createSession(dto, getNewSessionOrder(), getCurrentGenerationId())).getId();
    }

    /**
     * Session 을 수정하는 메서드입니다.
     * @param dto 수정할 Session의 정보
     * @return 수정된 Session의 ID
     */
    public Long updateSession(UpdateSessionReqDto dto) {
        Session findSession =
                sessionRepository.findById(dto.getSessionId()).orElseThrow(InvalidSessionIdException::new);

        findSession.updateSession(dto);

        return findSession.getId();
    }

    /**
     * 전체 세션 리스트를 조회하는 메서드입니다.
     * @return 세션 리스트
     */
    @Transactional(readOnly = true)
    public List<MultipleSessionResDto> getSessionList() {
        List<Session> findSessions = sessionRepository.getSessionByGenerationId(getCurrentGenerationId());
        List<MultipleSessionResDto> resultSessions = new ArrayList<>();

        for (Session session : findSessions) {
            resultSessions.add(new MultipleSessionResDto(session.getSessionKeyword(), session.getSessionContent(),
                    session.getSessionOrder(), session.getSessionStartTime()));
        }

        return resultSessions;
    }

    /**
     * Session 1개를 상세 조회하는 메서드입니다.
     * @param sessionId 조회하려는 세션 ID
     * @return 해당 세션의 정보
     */
    @Transactional(readOnly = true)
    public SingleSessionResDto getSingleSession(Long sessionId) {
        Session findSession =
                sessionRepository.findById(sessionId).orElseThrow(InvalidSessionIdException::new);

        return SingleSessionResDto.from(findSession);

    }

    /**
     * Session 1개를 삭제하는 메서드입니다.
     * @param sessionId 삭제하려는 세션 ID
     */
    public void deleteSession(Long sessionId) {
        sessionRepository.findById(sessionId).orElseThrow(InvalidSessionIdException::new);
        sessionRepository.deleteById(sessionId);
    }

    /**
     * 현재 진행 중인 기수의 ID 를 반환합니다.
     * TODO : Generation repository 와 연동
     * TODO : Generation 도메인으로 이관할 필요성 고려
     * @return 현재 진행 중인 세션의 ID
     */
    private Long getCurrentGenerationId() {
        return 1L;
    }

    /**
     * 현재 시점을 기준으로 생성하려는 세션의 주차를 반환합니다.
     * TODO : Generation repository 에서 가장 최근(현재)의 Generation 을 반환하는 로직
     * @return 생성하려는 세션의 주차
     */
    private int getNewSessionOrder() {
        return 1;
    }
}
