package com.nexters.momo.attendance.domain;

import com.nexters.momo.session.domain.Point;
import com.nexters.momo.session.domain.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.nexters.momo.attendance.domain.AttendanceStatus.ABSENT;
import static com.nexters.momo.attendance.domain.AttendanceStatus.ATTENDANCE;
import static com.nexters.momo.attendance.domain.AttendanceStatus.LATE;
import static com.nexters.momo.session.domain.Session.createSession;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Attendance 엔티티 관련 테스트 클래스입니다.
 *
 * @author CHO Min Ho
 */
@DisplayName("출석 도메인 테스트")
class AttendanceTest {

    private static final long TEST_MEMBER_ID = 2L;
    private static final long TEST_GENERATION_ID = 22L;

    @DisplayName("출석 생성 테스트")
    @Test
    void create_attendance_test() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime sessionEndTime = LocalDateTime.now().plusMinutes(100);
        LocalDateTime attendanceStartTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime attendanceEndTime = sessionEndTime;

        Session session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", "세부 주소", Point.of(20.002, 142.01), attendanceStartTime, attendanceEndTime, TEST_GENERATION_ID);

        AttendanceStatus attendanceStatus = session.judgeAttendanceStatus();

        // when
        Attendance attendance = Attendance.createAttendance(TEST_MEMBER_ID, session.getId(), attendanceStatus);

        // then
        assertThat(attendance.getStatus()).isEqualTo(ATTENDANCE);

    }

    @DisplayName("결석 테스트")
    @Test
    void absent_test() {
        // given
        LocalDateTime sessionStartTime = LocalDateTime.now().minusMinutes(100);
        LocalDateTime sessionEndTime = LocalDateTime.now().minusMinutes(10);
        LocalDateTime attendanceStartTime = LocalDateTime.now().minusMinutes(100);
        LocalDateTime attendanceEndTime = sessionEndTime;

        Session session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", "세부 주소", Point.of(20.002, 142.01), attendanceStartTime, attendanceEndTime, TEST_GENERATION_ID);

        // when
        Attendance attendance = Attendance.createAttendance(TEST_MEMBER_ID, session.getId(), session.judgeAttendanceStatus());

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

        Session session = createSession("세션 키워드", 1, "세션 내용", sessionStartTime, sessionEndTime,
                "서울시 강남구", "세부 주소", Point.of(20.002, 142.01), attendanceStartTime, attendanceEndTime, TEST_GENERATION_ID);

        // when
        Attendance attendance = Attendance.createAttendance(TEST_MEMBER_ID, session.getId(), session.judgeAttendanceStatus());

        // then
        assertThat(attendance.getStatus()).isEqualTo(LATE);
    }

}
