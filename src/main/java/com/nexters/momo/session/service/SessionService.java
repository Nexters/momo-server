package com.nexters.momo.session.service;

import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.dto.*;
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
    public Long createSession(SessionDto dto, Long generationId) {
        return sessionRepository.
                save(Session.createSession(dto.getTitle(), dto.getWeek(), dto.getContent(), dto.getStartAt(), dto.getEndAt(),
                        dto.getAddress(), dto.getAttendanceStartedAt(), dto.getAttendanceClosedAt(), generationId)).getId();
    }

    /**
     * Session 을 수정하는 메서드입니다.
     * @param id 해당 Session의 ID
     * @param dto 수정할 Session의 정보
     * @return 수정된 Session의 ID
     */
    public Long updateSession(Long id, SessionDto dto) {
        Session findSession =
                sessionRepository.findById(id).orElseThrow(InvalidSessionIdException::new);

        findSession.updateSession(dto.getTitle(), dto.getContent(), dto.getStartAt(), dto.getEndAt(),
                dto.getAddress(), dto.getAttendanceStartedAt(), dto.getAttendanceClosedAt());

        return id;
    }

    /**
     * 전체 세션 리스트를 조회하는 메서드입니다.
     * @return 세션 리스트
     */
    @Transactional(readOnly = true)
    public List<SessionRes> getSessionList(Long generationId) {
        List<Session> findSessions = sessionRepository.findSessionByGenerationId(generationId);
        List<SessionRes> resultSessions = new ArrayList<>();

        for (Session session : findSessions) {
            resultSessions.add(new SessionRes(SessionDto.from(session), session.getId(), session.getWeek()));
        }

        return resultSessions;
    }

    /**
     * Session 1개를 상세 조회하는 메서드입니다.
     * @param sessionId 조회하려는 세션 ID
     * @return 해당 세션의 정보
     */
    @Transactional(readOnly = true)
    public SessionRes getSingleSession(Long sessionId) {
        Session findSession =
                sessionRepository.findById(sessionId).orElseThrow(InvalidSessionIdException::new);

        return new SessionRes(SessionDto.from(findSession), findSession.getId(), findSession.getWeek());
    }

    /**
     * Session 1개를 삭제하는 메서드입니다.
     * @param sessionId 삭제하려는 세션 ID
     */
    public void deleteSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}
