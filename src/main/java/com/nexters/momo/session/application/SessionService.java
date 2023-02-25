package com.nexters.momo.session.application;

import com.nexters.momo.session.application.dto.SessionDto;
import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.domain.SessionRepository;
import com.nexters.momo.session.exception.InvalidSessionIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Session 관련 서비스 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;

    /**
     * Session 을 생성하는 메서드입니다.
     *
     * @param dto 생성할 Session의 정보
     * @return 생성된 Session 의 ID
     */
    public void createSession(SessionDto dto, Long generationId) {
        sessionRepository.save(
                Session.createSession(
                        dto.getTitle(), dto.getWeek(), dto.getContent(),
                        dto.getStartAt(), dto.getEndAt(),
                        dto.getAddress(), dto.getAddressDetail(), dto.getPoint(),
                        dto.getAttendanceStartedAt(), dto.getAttendanceClosedAt(),
                        generationId)
        );
    }

    /**
     * Session 을 수정하는 메서드입니다.
     *
     * @param id  해당 Session의 ID
     * @param dto 수정할 Session의 정보
     * @return 수정된 Session의 ID
     */
    @Transactional
    public void updateSession(Long id, SessionDto dto) {
        Session findSession =
                sessionRepository.findById(id).orElseThrow(InvalidSessionIdException::new);

        findSession.updateSession(
                dto.getTitle(), dto.getContent(),
                dto.getStartAt(), dto.getEndAt(),
                dto.getAddress(), dto.getAddressDetail(), dto.getPoint(),
                dto.getAttendanceStartedAt(), dto.getAttendanceClosedAt()
        );
    }

    /**
     * 활동 기수의 전체 세션 리스트를 조회하는 메서드입니다.
     *
     * @return 세션 리스트
     */
    @Transactional(readOnly = true)
    public List<SessionDto> getSessionList(Long generationId) {
        return sessionRepository.findSessionByGenerationId(generationId)
                .stream()
                .map(SessionDto::from)
                .collect(Collectors.toList());
    }

    /**
     * Session 1개를 상세 조회하는 메서드입니다.
     *
     * @param sessionId 조회하려는 세션 ID
     * @return 해당 세션의 정보
     */
    @Transactional(readOnly = true)
    public SessionDto getSingleSession(Long sessionId) {
        Session findSession =
                sessionRepository.findById(sessionId).orElseThrow(InvalidSessionIdException::new);

        return SessionDto.from(findSession);
    }

    public SessionDto getActiveSession(long generationId) {
        List<SessionDto> sessions = getSessionList(generationId);
        return getSoonOrToday(sessions).orElseThrow(() -> {
            throw new IllegalArgumentException("can not found active session");
        });
    }

    /**
     * Session 1개를 삭제하는 메서드입니다.
     *
     * @param sessionId 삭제하려는 세션 ID
     */
    public void deleteSession(long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    private Optional<SessionDto> getSoonOrToday(List<SessionDto> sessions) {
        LocalDateTime current = LocalDateTime.now();
        return sessions.stream()
                .filter(it -> current.isBefore(it.getStartAt()) || isToday(current, it.getStartAt()))
                .min(Comparator.comparing(SessionDto::getStartAt));
    }

    private boolean isToday(LocalDateTime current, LocalDateTime time) {
        return Duration.between(current, time).toDays() == 0;
    }

}
