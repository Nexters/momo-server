package com.nexters.momo.attendance.domain;

import com.nexters.momo.attendance.exception.InvalidAttendanceCodeException;
import com.nexters.momo.session.domain.Session;
import com.nexters.momo.session.dto.PostSessionReqDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static com.nexters.momo.attendance.domain.AttendanceStatus.ATTENDANCE;
import static com.nexters.momo.session.domain.Session.createSession;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Attendance 엔티티 관련 테스트 클래스입니다.
 *
 * @author CHO Min Ho
 */
@DisplayName("출석 엔티티 테스트")
class AttendanceTest {

    Session session;
    Integer attendanceCode;

    @DisplayName("출석 생성 테스트")
    @Test
    void createAttendanceTest() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime sessionEndTime = LocalDateTime.now().plusMinutes(100);

        session = createSession(new PostSessionReqDTO("세션 제목", sessionStartTime, sessionEndTime,
                "서울시 강남구"));

        attendanceCode = session.getAttendanceCode();

        // when
        Attendance attendance = Attendance.createAttendance(session, attendanceCode);

        // then
        assertThat(attendance.getStatus()).isEqualTo(ATTENDANCE);

    }

    @DisplayName("출석 코드 불일치 테스트")
    @Test
    void invalidAttendanceCodeTest() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime sessionEndTime = LocalDateTime.now().plusMinutes(100);

        session = createSession(new PostSessionReqDTO("세션 제목", sessionStartTime, sessionEndTime,
                "서울시 강남구"));

        attendanceCode = session.getAttendanceCode();

        // when, then
        assertThatThrownBy(() ->
                Attendance.createAttendance(session, attendanceCode + 1)).
                isInstanceOf(InvalidAttendanceCodeException.class);
    }


}