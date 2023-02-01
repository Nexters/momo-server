package com.nexters.momo.attendance.domain;

import com.nexters.momo.attendance.exception.InvalidAttendanceCodeException;
import com.nexters.momo.attendance.exception.TooFastAttendanceTimeException;
import com.nexters.momo.common.BaseTimeEntity;
import com.nexters.momo.session.domain.Session;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

import static com.nexters.momo.attendance.domain.AttendanceStatus.*;

/**
 * 각 세션의 출석을 나타내는 Attendance 엔티티입니다.
 * TODO : Member 엔티티와 연관관계 매핑
 * TODO : 출석 엔티티를 미리 생성하고 출석체크 시에 status를 변경하는 방식으로 할 것인지, 출석체크 시에 엔티티를 생성하는 방식으로 할지 결정
 *
 * @author CHO Min Ho
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseTimeEntity {
    private static final int MINIMUM_ATTENDANCE_TIME = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private Attendance(AttendanceStatus status, Long sessionId) {
        this.status = status;
        this.sessionId = sessionId;
    }

    /**
     * Attendance 엔티티를 생성하는 메서드입니다.
     * Attendance 엔티티는 해당 메서드로만 생성됩니다.
     * @param session 해당 주차의 세션
     * @param attendanceCode 출석 코드
     * @return 생성된 Attendance 엔티티
     */
    public static Attendance createAttendance(Session session, Integer attendanceCode) {
        if (!session.isSameAttendanceCode(attendanceCode)) {
            // 출석 코드 불일치
            throw new InvalidAttendanceCodeException();
        }

        return new Attendance(judgeAttendanceStatus(session), session.getId());
    }

    /**
     * 세션 출석 시간과 비교하여 지각, 결석 여부 등을 반환합니다.
     * @param session 비교하고자 하는 세션
     * @return 현재 시간에서의 출석 상태
     */
    private static AttendanceStatus judgeAttendanceStatus(Session session) {
        LocalDateTime currentTime = LocalDateTime.now();

        // 1. 아직 출석할 수 없는 시간일 경우
        if (session.getAttendanceStartTime().isAfter(currentTime)) {
            throw new TooFastAttendanceTimeException();
        }

        // 2. 출석 마감 시간 이후 출석을 시도하는 경우 - 결석
        if (currentTime.isAfter(session.getAttendanceEndTime())) {
            return ABSENT;
        }

        // 3. 지각
        if (currentTime.isAfter(session.getSessionStartTime())) {
            return LATE;
        }

        // 4. 정상 출석
        return ATTENDANCE;
    }
}
