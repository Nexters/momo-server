package com.nexters.momo.attendance.domain;

import com.nexters.momo.attendance.exception.InvalidAttendanceCodeException;
import com.nexters.momo.session.domain.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static com.nexters.momo.attendance.domain.AttendanceStatus.*;
import static com.nexters.momo.session.domain.Session.createSession;
import static org.assertj.core.api.Assertions.*;

/**
 * Attendance 엔티티 관련 테스트 클래스입니다.
 *
 * @author CHO Min Ho
 */
@DisplayName("출석 도메인 테스트")
class AttendanceTest {

    Session session;
    Integer attendanceCode;
    Integer incorrectAttendanceCode;
    Long generationId = 1L;
    Long memberId = 2L;

    @DisplayName("출석 생성 테스트")
    @Test
    void create_attendance_test() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime sessionEndTime = LocalDateTime.now().plusMinutes(100);
        LocalDateTime attendanceStartTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime attendanceEndTime = sessionEndTime;

        session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", attendanceStartTime, attendanceEndTime, generationId);

        attendanceCode = session.getAttendanceCode();

        // when
        Attendance attendance = Attendance.createAttendance(session, memberId, attendanceCode);

        // then
        assertThat(attendance.getStatus()).isEqualTo(ATTENDANCE);

    }

    @DisplayName("출석 코드 불일치 테스트")
    @Test
    void invalid_attendanceCode_test() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime sessionEndTime = LocalDateTime.now().plusMinutes(100);
        LocalDateTime attendanceStartTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime attendanceEndTime = sessionEndTime;

        session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", attendanceStartTime, attendanceEndTime, generationId);

        incorrectAttendanceCode = session.getAttendanceCode() + 1;

        // when, then
        assertThatThrownBy(() ->
                Attendance.createAttendance(session, memberId, incorrectAttendanceCode)).
                isInstanceOf(InvalidAttendanceCodeException.class);
    }

    @DisplayName("결석 테스트")
    @Test
    void absent_test() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().minusMinutes(100);
        LocalDateTime sessionEndTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime attendanceStartTime = LocalDateTime.now().minusMinutes(100);
        LocalDateTime attendanceEndTime = sessionEndTime;

        session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", attendanceStartTime, attendanceEndTime, generationId);

        attendanceCode = session.getAttendanceCode();

        // when
        Attendance attendance = Attendance.createAttendance(session, memberId, attendanceCode);

        // then
        assertThat(attendance.getStatus()).isEqualTo(ABSENT);
    }

    @DisplayName("지각 테스트")
    @Test
    void late_test() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime sessionEndTime = LocalDateTime.now().plusMinutes(100);
        LocalDateTime attendanceStartTime = LocalDateTime.now().minusMinutes(50);
        LocalDateTime attendanceEndTime = sessionEndTime;

        session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", attendanceStartTime, attendanceEndTime, generationId);

        attendanceCode = session.getAttendanceCode();

        // when
        Attendance attendance = Attendance.createAttendance(session, memberId, attendanceCode);

        // then
        assertThat(attendance.getStatus()).isEqualTo(LATE);
    }


}